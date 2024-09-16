package cdio.desert_eagle.project_bts.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cdio.desert_eagle.project_bts.repository.BaseResult;

public class UpdateProfileDialog extends DialogFragment {

    private final BaseResult<Boolean> listener;
    private final String title;
    private final String message;
    private final String positiveMessage;

    public UpdateProfileDialog(String title,
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
                .setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSuccess(true);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSuccess(false);
                    }
                });
        return builder.create();
    }
}
