package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    EditText emailAddressField;
    EditText passwordField;
    private HashMap<String, String> users = new HashMap<>();
    private final String myEmail = "qwe";
    private final String myPassword = "123";

    DialogMessage dialogMessage = new DialogMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        users.put(myEmail, myPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String registeredMail= null;
        String registeredPass = null;
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
        {
            registeredMail = arguments.get("email").toString();
            registeredPass = arguments.get("pass").toString();
        }
        users.put(registeredMail, registeredPass);
    }

    public void signIn(View view) {
        Intent intent = new Intent(Login.this, Calculator.class);
        String inputEmail = String.valueOf(emailAddressField.getText());
        String inputPassword = String.valueOf(passwordField.getText());

        if(users.containsKey(inputEmail))
        {
            if(users.get(inputEmail).equals(inputPassword))
            {
                startActivity(intent);
            }
            else
            {
                dialogMessage.title="Invalid password.";
                dialogMessage.show(getSupportFragmentManager(), "t");
            }
        }
        else
        {
            dialogMessage.title="The email you provided has not been registered.";
            dialogMessage.show(getSupportFragmentManager(), "r");
        }
    }

    public void register(View view)
    {
        Intent intent = new Intent(Login.this, Register.class);
        try
        {
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}