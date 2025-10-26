package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class VerificationActivity extends AppCompatActivity {

    public static final String RESTORE_TYPE = "restore";
    public static final String SIGNUP_TYPE = "signup";
    public static final String UPDATE_DATA_TYPE = "update";

    int TestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        TextView text = (TextView) findViewById(R.id.message_code);
        EditText code1 = (EditText) findViewById(R.id.verification_code1);
        EditText code2 = (EditText) findViewById(R.id.verification_code2);
        EditText code3 = (EditText) findViewById(R.id.verification_code3);
        EditText code4 = (EditText) findViewById(R.id.verification_code4);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        TextView resendCode = (TextView) findViewById(R.id.resend_code);

        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    code2.requestFocus();
                }
            }
        });

        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    code3.requestFocus();
                } else if (s.length() == 0) {
                    code1.requestFocus();
                }
            }
        });

        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    code4.requestFocus();
                } else if (s.length() == 0) {
                    code2.requestFocus();
                }
            }
        });

        code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    code3.requestFocus();
                } else if (s.length() == 1) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(code4.getWindowToken(), 0);
                    code4.clearFocus();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String emailOrPhone = intent.getStringExtra("EmailOrPhoneNumber");
        String data = intent.getStringExtra("data");

        if (emailOrPhone.equals("email")) {
            text.setText("Мы отправили сообщение с кодом на электронную почту " + data);
        }else if (emailOrPhone.equals("phoneNumber")) {
            text.setText("Мы отправили сообщение с кодом сообщением на мобильный телефон " + data);
        }

        if (type.equals(SIGNUP_TYPE)) {
            submitButton.setText("Продолжить");
        }else if (type.equals(RESTORE_TYPE)) {
            submitButton.setText("Восстановить");
        }

        Random r = new Random();
        TestCode = r.nextInt(8999)+1000;

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestCode = r.nextInt(8999) + 1000;
                Toast.makeText(getApplicationContext(), "Ваш код: " + String.valueOf(TestCode), Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(getApplicationContext(), "Ваш код: " + String.valueOf(TestCode), Toast.LENGTH_LONG).show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code1.getText().toString() + "" + code2.getText().toString() + "" + code3.getText().toString() + "" + code4.getText().toString();
                System.out.println(code);
                if (Integer.parseInt(code) == TestCode) {
                    if (type.equals(SIGNUP_TYPE)) {
                        Intent intent1 = new Intent(getApplicationContext(), CreateActivityActivity.class);
                        intent1.putExtra("data", data);
                        startActivity(intent1);
                    }else if (type.equals(RESTORE_TYPE)) {
                        Intent intent1 = new Intent(getApplicationContext(), UpdatePasswordActivity.class);
                        intent1.putExtra("data", data);
                        startActivity(intent1);
                    }
                }else {
                    code1.setText("");
                    code2.setText("");
                    code3.setText("");
                    code4.setText("");
                    code1.requestFocus();
                    Toast.makeText(getApplicationContext(), "Неверный код!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
