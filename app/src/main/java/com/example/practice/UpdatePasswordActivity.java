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

public class UpdatePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updatepass);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        EditText password = (EditText) findViewById(R.id.user_password);
        EditText passwordConfirm = (EditText) findViewById(R.id.user_password_confirm);
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
                TextView passwordInvalid = (TextView) findViewById(R.id.password_requirements);
                TextView passwordConfirmInvalid = (TextView) findViewById(R.id.user_password_confirm_invalid);

                passwordConfirmInvalid.setText("");
                passwordInvalid.setTextColor(Color.parseColor("#838589"));

                if (Validator.PasswordValidator(password.getText().toString()) && password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putBoolean(MainActivity.SP_IS_USER_LOGGED_IN, true);
                    spEditor.putString(MainActivity.SP_USER_PASSWORD, password.getText().toString());
                    spEditor.apply();
                    Toast.makeText(getApplicationContext(), "Рады вас видеть снова " + sp.getString(MainActivity.SP_USER_NAME, "") + "!", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                }


                if (!Validator.PasswordValidator(password.getText().toString())) {
                    passwordInvalid.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }

                if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    passwordConfirmInvalid.setText("Пароли не совпадают!");
                }
            }
        });
    }
}
