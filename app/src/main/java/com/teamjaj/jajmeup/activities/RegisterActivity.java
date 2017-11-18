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
import com.teamjaj.jajmeup.utilities.TextUtils;

public class RegisterActivity extends AppCompatActivity {

    private final UserService userService = new UserService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button submit = (Button) findViewById(R.id.validerregister);
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        EditText emailTextEdit = (EditText) findViewById(R.id.mailReg);
                        String email = emailTextEdit.getText().toString();
                        EditText pseudoTextEdit = (EditText) findViewById(R.id.pseudonyme);
                        String pseudo = pseudoTextEdit.getText().toString();
                        EditText passwordTextEdit = (EditText) findViewById(R.id.motdepasseReg);
                        String password = passwordTextEdit.getText().toString();
                        if (email.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Aucun email saisi", Toast.LENGTH_SHORT).show();
                        } else if (pseudo.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Aucun pseudonyme saisi", Toast.LENGTH_SHORT).show();
                        } else if (password.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Aucun mot de passe saisi", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isValidEmail(email)) {
                            Toast.makeText(getApplicationContext(), "Email invalide", Toast.LENGTH_SHORT).show();
                        } else {
                            userService.register(getApplicationContext(), email, pseudo, password);
                        }
                    }
                }
        );
    }
}
