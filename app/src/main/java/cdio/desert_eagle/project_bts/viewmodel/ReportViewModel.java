package cdio.desert_eagle.project_bts.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cdio.desert_eagle.project_bts.model.response.Report;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.repository.report.ReportRepository;
import cdio.desert_eagle.project_bts.repository.report.ReportRepositoryImpl;

public class ReportViewModel extends ViewModel {

    private final ReportRepository reportRepository;
    public MutableLiveData<String> reportSuccessLiveData;
    public MutableLiveData<String> errorLiveData;

    public ReportViewModel() {
        this.reportRepository = new ReportRepositoryImpl();
        reportSuccessLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public void reportPost(Long userId, Long postId, String reason) {
        reportRepository.reportPost(userId, postId, reason, new ReportRepository.ReportResultRepository<ResponseObject<Report>>() {
            @Override
            public void onSuccess(ResponseObject<Report> response) {
                if (response.getData() == null) {
                    errorLiveData.postValue(response.getMessage());
                } else {
                    reportSuccessLiveData.postValue("Thank you for your report");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
