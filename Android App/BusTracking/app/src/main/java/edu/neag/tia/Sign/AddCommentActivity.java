package edu.neag.tia.Sign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.neag.tia.R;

public class AddCommentActivity extends AppCompatDialogFragment {

    private UpdateDialogListener listener;
    private EditText etComment;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_comment, null);

        builder.setView(view)
                .setTitle("Add News")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.applyText(etComment.getText().toString());

                    }

                });

        etComment = view.findViewById(R.id.etComment);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface UpdateDialogListener {
        void applyText(String comment);
    }
}