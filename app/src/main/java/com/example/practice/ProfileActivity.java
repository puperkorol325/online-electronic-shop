package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private final int SELECT_PICTURE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_profile);

            ImageView backButton = (ImageView) findViewById(R.id.get_back_to_main);
            TextView pageName = (TextView) findViewById(R.id.page_name);
            TextView userLogin = (TextView) findViewById(R.id.user_login);
            EditText userName = (EditText) findViewById(R.id.user_name);
            TextView userPassword = (EditText) findViewById(R.id.user_password);
            ImageView userPicture = (ImageView) findViewById(R.id.profile_pic);
            Button saveInfo = (Button) findViewById(R.id.submit_button);
            Button leaveFromAccount = (Button) findViewById(R.id.leave_account_button);

            ImageView homeSvg = (ImageView) findViewById(R.id.home_drawable);
            DrawableCompat.setTint(DrawableCompat.wrap(homeSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryDarker));

            ImageView loginSvg = (ImageView) findViewById(R.id.login_drawable);
            DrawableCompat.setTint(DrawableCompat.wrap(loginSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryBlue));

            TextView tv = (TextView) findViewById(R.id.to_login_text);
            tv.setText("Профиль");
            LinearLayout toMain = (LinearLayout) findViewById(R.id.bottom_panel_to_main_page);
            LinearLayout toWishlist = (LinearLayout) findViewById(R.id.bottom_panel_to_wishlist);

            toMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            toWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(intent);
                }
            });


            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            userPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_PICTURE);
                }
            });

            SharedPreferences sp = getSharedPreferences("MegaMall", Context.MODE_PRIVATE);

            saveInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newUserName = userName.getText().toString();
                    String newUserPassword = userPassword.getText().toString();
                    String newUserPicture = null;
                    if (userPicture.getTag() != null) {
                        newUserPicture = saveImageToStorage((Uri) userPicture.getTag());
                    }

                    TextView passwordInvalid = (TextView) findViewById(R.id.password_requirements);
                    TextView nameInvalid = (TextView) findViewById(R.id.name_invalid);
                    passwordInvalid.setTextColor(Color.parseColor("#838589"));
                    nameInvalid.setText("");

                    if (!Validator.PasswordValidator(newUserPassword)) {
                        passwordInvalid.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    if (!Validator.NameValidator(newUserName)) {
                        nameInvalid.setText("Имя должно состоять минимум из трех символов");
                    }

                    if (Validator.PasswordValidator(newUserPassword) && Validator.NameValidator(newUserName)) {
                        SharedPreferences.Editor spEdit = sp.edit();
                        spEdit.putString(MainActivity.SP_USER_NAME, newUserName);
                        spEdit.putString(MainActivity.SP_USER_PASSWORD, newUserPassword);
                        spEdit.putString(MainActivity.SP_USER_PICTURE, newUserPicture);
                        spEdit.apply();
                        Toast.makeText(getApplicationContext(), "Данные успешно сохранены", Toast.LENGTH_LONG).show();
                    }
                }
            });

            String spName = sp.getString(MainActivity.SP_USER_NAME, "");
            String spLogin = sp.getString(MainActivity.SP_USER_PHONE_OR_EMAIL, "");
            String spPassword = sp.getString(MainActivity.SP_USER_PASSWORD, "");
            String spPicture = sp.getString(MainActivity.SP_USER_PICTURE, null);

            pageName.setText("Профиль");
            userLogin.setText(spLogin);
            userName.setText(spName);
            userPassword.setText(spPassword);
            if (spPicture != null) {
                userPicture.setImageBitmap(BitmapFactory.decodeFile(spPicture));
                userPicture.setTag(Uri.parse(spPicture));
            };

            leaveFromAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sp.edit().putBoolean(MainActivity.SP_IS_USER_LOGGED_IN, false).apply();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    private String saveImageToStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            File file = new File(getFilesDir(), "profile_picture.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && intent != null && intent.getData() != null) {
                ImageView userPicture = (ImageView) findViewById(R.id.profile_pic);
                Uri uriImage = intent.getData();
                userPicture.setImageURI(uriImage);
                userPicture.setTag(uriImage);
            }
        }
}
