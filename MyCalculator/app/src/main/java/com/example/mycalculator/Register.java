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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailAddressField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        confirmPasswordField = (EditText)findViewById(R.id.confirm_password);
    }

    public void validateRegistration(View view) {
        Intent intent = new Intent(Register.this,Login.class);
        String mail= String.valueOf(emailAddressField.getText());
        String pass= String.valueOf(passwordField.getText());
        String secPass= String.valueOf(confirmPasswordField.getText());
        if(pass.equals(secPass)) {
            intent.putExtra("email", mail);
            intent.putExtra("pass", pass);
            startActivity(intent);
        }
        else {
            System.out.println("Passwords don't match.");
        }
    }
}