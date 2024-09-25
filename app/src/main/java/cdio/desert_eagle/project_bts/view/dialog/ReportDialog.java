package cdio.desert_eagle.project_bts.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cdio.desert_eagle.project_bts.R;
import cdio.desert_eagle.project_bts.viewmodel.ReportViewModel;

public class ReportDialog extends DialogFragment {

    private final Long userId;
    private final Long postId;

    public ReportDialog(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_dialog, container, false);
        EditText etReason = view.findViewById(R.id.etReason);
        Button btnSend = view.findViewById(R.id.btnSend);
        ReportViewModel reportViewModel = new ReportViewModel();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportViewModel.reportPost(userId, postId, etReason.getText().toString().trim());
            }
        });
        reportViewModel.reportSuccessLiveData.observe(requireActivity(), data -> {
            Toast.makeText(requireActivity(), data, Toast.LENGTH_SHORT).show();
            dismiss();
        });
        reportViewModel.errorLiveData.observe(requireActivity(), message -> {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }
}
