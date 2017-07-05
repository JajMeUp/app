package com.teamjaj.agourd.valoulou.jajmeup.fragments;

/**
 * Created by Valentin on 25/09/2016.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;

public class DialogFragmentMessage extends DialogFragment {

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       AlertDialog.Builder messageAlter = new AlertDialog.Builder(getActivity());

       messageAlter.setTitle("Voulez-vous envoyer un message ?");

       LayoutInflater inflater = getActivity().getLayoutInflater();

       messageAlter.setView(inflater.inflate(R.layout.send_message, null))

               .setNegativeButton(R.string.notsend, new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Caller.setClockSong("");
                   }
               })

                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText message = (EditText) getDialog().findViewById(R.id.message);
                        System.out.println("Le beau message -> " + message.getText());
                        Caller.setClockSong(String.valueOf(message.getText()));
                    }
                });

       return messageAlter.create();

   }
    public static DialogFragmentMessage newInstance() {
        DialogFragmentMessage f = new DialogFragmentMessage();
        return f;
    }
}
