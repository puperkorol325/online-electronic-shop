package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class CreateActivityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createaccount);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        EditText name = (EditText) findViewById(R.id.user_name);
        EditText password = (EditText) findViewById(R.id.user_password);
        EditText passwordConfirm = (EditText) findViewById(R.id.user_password_confirm);
        EditText inviteCode = (EditText) findViewById(R.id.user_request_code);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String emailOrPhone = intent.getStringExtra("data");

        SharedPreferences sp = getSharedPreferences("MegaMall", Context.MODE_PRIVATE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nameInvalid = (TextView) findViewById(R.id.name_invalid);
                TextView passwordInvalid = (TextView) findViewById(R.id.password_requirements);
                TextView passwordConfirmInvalid = (TextView) findViewById(R.id.user_password_confirm_invalid);

                nameInvalid.setText("");
                passwordInvalid.setTextColor(Color.parseColor("#838589"));
                passwordConfirmInvalid.setText("");

                if (Validator.NameValidator(name.getText().toString()) && Validator.PasswordValidator(password.getText().toString()) && password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putBoolean(MainActivity.SP_IS_USER_LOGGED_IN, true);
                    spEditor.putString(MainActivity.SP_USER_NAME, name.getText().toString());
                    spEditor.putString(MainActivity.SP_USER_PASSWORD, password.getText().toString());
                    spEditor.putString(MainActivity.SP_USER_PHONE_OR_EMAIL, emailOrPhone);
                    spEditor.putString(MainActivity.SP_USER_PICTURE, null);
                    spEditor.putStringSet(MainActivity.SP_FAVORITES, Set.of());
                    spEditor.apply();
                    Toast.makeText(getApplicationContext(), "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                }

                if (!Validator.NameValidator(name.getText().toString())) {
                    nameInvalid.setText("Имя должно содержать минимум 3 символа");
                }

                if (!Validator.PasswordValidator(password.getText().toString())) {
                    passwordInvalid.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }

                if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    passwordConfirmInvalid.setText("Пароли должны совпадать!");
                }
            }
        });
    }
}
