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
import com.teamjaj.agourd.valoulou.jajmeup.R;

/**
 * Created by ektoplasma on 25/08/16.
 */
public class LoginActivity extends AppCompatActivity{

    Context ctx = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button ButtonValide = (Button)findViewById(R.id.validerlogin);

        assert ButtonValide != null;
        ButtonValide.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        EditText user = (EditText) findViewById(R.id.username);
                        assert user != null;
                        String checkuser = user.getText().toString();
                        EditText mdp = (EditText) findViewById(R.id.motdepasse);
                        assert mdp != null;
                        String checkmdp = mdp.getText().toString();
                        if (checkuser.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun nom d'utilisateur saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (checkmdp.matches("")) {
                            Toast.makeText(getApplicationContext(), "Aucun mot de passe saisi", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Caller.setCtx(ctx);
                            Caller.signin(checkuser, checkmdp);
                        }
                    }
                }
        );
    }

}
