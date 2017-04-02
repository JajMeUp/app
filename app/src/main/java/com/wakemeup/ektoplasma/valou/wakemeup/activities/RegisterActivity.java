package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;
import com.wakemeup.ektoplasma.valou.wakemeup.R;

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
                        EditText user = (EditText) findViewById(R.id.usernameReg);
                        assert user != null;
                        String checkuser = user.getText().toString();
                        EditText pseudo = (EditText) findViewById(R.id.pseudonyme);
                        assert pseudo != null;
                        String checkpseudo = pseudo.getText().toString();
                        EditText mdp = (EditText) findViewById(R.id.motdepasseReg);
                        assert mdp != null;
                        String checkmdp = mdp.getText().toString();
                        if (checkuser.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun nom d'utilisateur saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkpseudo.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun pseudonyme saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkmdp.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun mot de passe saisi", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Caller.setCtx(ctx);
                            Caller.signup(checkuser, checkpseudo, checkmdp);
                        }
                    }
                }
        );
    }


}
