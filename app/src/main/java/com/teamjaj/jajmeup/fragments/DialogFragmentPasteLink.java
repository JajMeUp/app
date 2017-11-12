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
import com.teamjaj.jajmeup.services.ClockService;

public class DialogFragmentPasteLink extends DialogFragment {

    public final ClockService clock = new ClockService();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder messageAlter = new AlertDialog.Builder(getActivity());

        messageAlter.setTitle("Il est temps de réveiller quelqu'un");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        messageAlter.setView(inflater.inflate(R.layout.dialoglinkyt, null))

                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText lienyt = (EditText) getDialog().findViewById(R.id.youtubelink);
                        EditText message = (EditText) getDialog().findViewById(R.id.messagereveil);
                        Log.d("Link", String.valueOf(lienyt.getText()));
                        Log.d("Message", String.valueOf(message.getText()));
                        Bundle mArgs = getArguments();
                        String target = mArgs.getString("target");
                        Log.d("Target", target);
                        clock.makeVote(getContext(), String.valueOf(lienyt.getText()), String.valueOf(message.getText()), Long.parseLong(target));
                        /*Caller.setCurrentLink(String.valueOf(message.getText()));
                        DialogFragmentMessage dialogMessage = DialogFragmentMessage.newInstance();
                        dialogMessage.show(getFragmentManager(), "fragmentDialog");*/
                    }
                });

        return messageAlter.create();

    }

    public static DialogFragmentPasteLink newInstance() {
        DialogFragmentPasteLink f = new DialogFragmentPasteLink();
        return f;
    }
}
