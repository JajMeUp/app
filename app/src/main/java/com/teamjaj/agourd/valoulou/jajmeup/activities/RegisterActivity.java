package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ektoplasma on 25/08/16.
 */
public class RegisterActivity extends AppCompatActivity{

    Context ctx = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button ButtonValide = (Button)findViewById(R.id.validerregister);

        assert ButtonValide != null;
        ButtonValide.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        EditText user = (EditText) findViewById(R.id.mailReg);
                        assert user != null;
                        String checkmail = user.getText().toString();
                        EditText pseudo = (EditText) findViewById(R.id.pseudonyme);
                        assert pseudo != null;
                        String checkpseudo = pseudo.getText().toString();
                        EditText mdp = (EditText) findViewById(R.id.motdepasseReg);
                        assert mdp != null;
                        String checkmdp = mdp.getText().toString();
                        if (checkmail.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun email saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkpseudo.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun pseudonyme saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkmdp.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun mot de passe saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (!isEmailValid(checkmail)) {
                            Toast.makeText(getApplicationContext(), "Email invalide", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Caller.setCtx(ctx);
                            Caller.register(ctx, checkmail, checkpseudo, checkmdp);
//                            Caller.signup(checkmail, checkpseudo, checkmdp);
                        }
                    }
                }
        );
    }

    /*Si la fonction retourne True alors c'est un mail*/
    public static boolean isEmailValid(String email) {
        String mailexpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(mailexpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
