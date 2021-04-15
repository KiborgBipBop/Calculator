package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText emailAddressField;
    EditText passwordField;
    EditText confirmPasswordField;

    DialogMessage dialogMessage = new DialogMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailAddressField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        confirmPasswordField = (EditText)findViewById(R.id.confirm_password);
    }

    public void validateRegistration(View view) {
        String patternPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}";
        String patternMail = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
        Intent intent = new Intent(Register.this,Login.class);
        String mail= String.valueOf(emailAddressField.getText());
        String pass= String.valueOf(passwordField.getText());
        String secPass= String.valueOf(confirmPasswordField.getText());
        if (mail.matches(patternMail) && pass.matches(patternPass)) {
            if(pass.equals(secPass)) {
                intent.putExtra("email", mail);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
            else {
                dialogMessage.title = "Passwords don't match.";
                dialogMessage.show(getSupportFragmentManager(), "custom");
            }
        }
        else {
            dialogMessage.title="Invalid password or email address.";
            dialogMessage.show(getSupportFragmentManager(),"u");
        }
    }
}