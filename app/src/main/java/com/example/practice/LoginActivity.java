package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView toSignUp = (TextView) findViewById(R.id.to_signup);
        TextView toForgotPassword = (TextView) findViewById(R.id.to_forgot_password);

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        toForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        EditText emailOrPhone = (EditText) findViewById(R.id.user_email_phone);
        EditText password = (EditText) findViewById(R.id.user_password);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        SharedPreferences sp = getSharedPreferences("MegaMall", Context.MODE_PRIVATE);
        String userEmailOrPhone =  sp.getString(MainActivity.SP_USER_PHONE_OR_EMAIL, null);
        String userPassword =  sp.getString(MainActivity.SP_USER_PASSWORD, null);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailOrPhone.getText().toString().equals(userEmailOrPhone) && password.getText().toString().equals(userPassword)) {
                    sp.edit().putBoolean(MainActivity.SP_IS_USER_LOGGED_IN, true).apply();
                    Toast.makeText(getApplicationContext(), "Рады вас видеть снова " + sp.getString(MainActivity.SP_USER_NAME, "") + "!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
