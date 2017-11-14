package com.teamjaj.jajmeup.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.services.ClockService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogFragmentPasteLink extends DialogFragment {

    public final ClockService clock = new ClockService();
    public EditText lienytedit;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder messageAlter = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoglinkyt, null);

        messageAlter.setTitle("Il est temps de réveiller quelqu'un");

        lienytedit = (EditText) dialogView.findViewById(R.id.youtubelink);
        Bundle mArgs = getArguments();
        if (mArgs.getString("sharedlink") != "")
        {
            lienytedit.setText(mArgs.getString("sharedlink"), TextView.BufferType.EDITABLE);
        }

        messageAlter.setView(dialogView)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        lienytedit = (EditText) getDialog().findViewById(R.id.youtubelink);
                        EditText message = (EditText) getDialog().findViewById(R.id.messagereveil);
                        String lienyt = String.valueOf(lienytedit.getText());

                        if(Patterns.WEB_URL.matcher(lienyt).matches())
                        {
                            String videoid = checkYouTubeLink(lienyt);
                            if (videoid != null)
                            {
                                Log.d("Link", videoid);
                                Log.d("Message", String.valueOf(message.getText()));
                                Bundle mArgs = getArguments();
                                String target = mArgs.getString("target");
                                Log.d("Target", target);
                                clock.makeVote(getContext(), videoid, String.valueOf(message.getText()), Long.parseLong(target));
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Lien Youtube invalide", Toast.LENGTH_SHORT).show();
                                Log.d("BadLink", "It is not a YouTube link");
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Lien Youtube invalide", Toast.LENGTH_SHORT).show();
                            Log.d("BadLink", "It is not a YouTube link");
                        }
                    }

                });

        /*lienytedit = (EditText) getDialog().findViewById(R.id.youtubelink);
        Bundle mArgs = getArguments();*/


        return messageAlter.create();

    }

    public static DialogFragmentPasteLink newInstance() {
        DialogFragmentPasteLink f = new DialogFragmentPasteLink();
        return f;
    }

    private String checkYouTubeLink(String url)
    {
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
        if (url.matches(pattern))
        {
            pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(url);
            if (matcher.find()) {
                return matcher.group();
            }
            return null;
        }

        return null;
    }
}
