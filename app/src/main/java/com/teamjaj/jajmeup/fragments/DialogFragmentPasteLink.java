package com.teamjaj.jajmeup.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.utilities.Caller;

public class DialogFragmentPasteLink extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder messageAlter = new AlertDialog.Builder(getActivity());

        messageAlter.setTitle("Copier le lien YouTube");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        messageAlter.setView(inflater.inflate(R.layout.dialoglinkyt, null))

                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText message = (EditText) getDialog().findViewById(R.id.youtubelink);
                        Log.d("Link", String.valueOf(message.getText()));
                        Caller.setCurrentLink(String.valueOf(message.getText()));
                        DialogFragmentMessage dialogMessage = DialogFragmentMessage.newInstance();
                        dialogMessage.show(getFragmentManager(), "fragmentDialog");
                    }
                });

        return messageAlter.create();

    }

    public static DialogFragmentPasteLink newInstance() {
        DialogFragmentPasteLink f = new DialogFragmentPasteLink();
        return f;
    }
}
