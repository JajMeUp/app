package com.teamjaj.jajmeup.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.services.UserService;

public class LoginActivity extends AppCompatActivity{

    private final UserService userService = new UserService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submit = (Button)findViewById(R.id.validerlogin);
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        EditText usernameTextEdit = (EditText) findViewById(R.id.username);
                        String username = usernameTextEdit.getText().toString();
                        EditText passwordTextEdit = (EditText) findViewById(R.id.motdepasse);
                        String password = passwordTextEdit.getText().toString();
                        if (username.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Aucun nom d'utilisateur saisi", Toast.LENGTH_SHORT).show();
                        }
                        else if (password.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Aucun mot de passe saisi", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userService.login(getApplicationContext(), username, password);
                        }
                    }
                }
        );
    }

}
