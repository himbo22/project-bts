package cdio.desert_eagle.project_bts.repository.report;

import cdio.desert_eagle.project_bts.model.response.Report;
import cdio.desert_eagle.project_bts.model.response.ResponseObject;
import cdio.desert_eagle.project_bts.listener.BaseResult;

public interface ReportRepository {
    void reportPost(Long userId, Long postId, String reason, ReportResultRepository<ResponseObject<Report>> listener);

    interface ReportResultRepository<T> extends BaseResult<T> {
    }
}
