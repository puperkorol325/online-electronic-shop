package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText emailOrPhoneNumber = (EditText) findViewById(R.id.user_email_phone);
        Button registrateButton = (Button) findViewById(R.id.signup_button);
        TextView invalid = (TextView) findViewById(R.id.user_email_phone_invalid);

        registrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validator.EmailValidator(emailOrPhoneNumber.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                    intent.putExtra("type", VerificationActivity.SIGNUP_TYPE);
                    intent.putExtra("EmailOrPhoneNumber", "email");
                    intent.putExtra("data", emailOrPhoneNumber.getText().toString());
                    invalid.setText("");
                    startActivity(intent);
                }else if (Validator.PhoneNumberValidator(emailOrPhoneNumber.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                    intent.putExtra("type", VerificationActivity.SIGNUP_TYPE);
                    intent.putExtra("EmailOrPhoneNumber", "phoneNumber");
                    intent.putExtra("data", emailOrPhoneNumber.getText().toString());
                    invalid.setText("");
                    startActivity(intent);
                }else {
                    invalid.setText("Неверный формат данных");
                }
            }
        });
    }

}
