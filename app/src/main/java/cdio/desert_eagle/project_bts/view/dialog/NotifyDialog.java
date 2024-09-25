package cdio.desert_eagle.project_bts.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cdio.desert_eagle.project_bts.listener.BaseResult;

public class NotifyDialog extends DialogFragment {

    private final BaseResult<Boolean> listener;
    private final String title;
    private final String message;
    private final String positiveMessage;

    public NotifyDialog(String title,
                        String message,
                        String positiveMessage,
                        BaseResult<Boolean> listener) {
        this.listener = listener;
        this.title = title;
        this.message = message;
        this.positiveMessage = positiveMessage;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveMessage, (dialog, which) -> listener.onSuccess(true))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onSuccess(false));
        return builder.create();
    }
}
