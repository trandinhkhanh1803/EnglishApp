package com.example.aoneenglish.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.MainActivity;
import com.example.aoneenglish.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnDangnhap;
    TextView tvDangky, tvforgotPassword;
    EditText edttaikhoan, edtmatkhau;
    DatabaseAccess DB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        AnhXa();
        DB = DatabaseAccess.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //Đăng nhập thành công chuyển sang MainActivity
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edttaikhoan.getText().toString().trim();
                String matkhau = edtmatkhau.getText().toString().trim();


                // validations for input email and password // check th trong
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Hãy nhập Email của bạn!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(getApplicationContext(),
                            "Hãy nhập mật khẩu của bạn!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // signin existing user

                mAuth.signInWithEmailAndPassword(email, matkhau)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Logged in successfully!!",
                                                    Toast.LENGTH_LONG)
                                                    .show();

                                            DB.iduser = mAuth.getCurrentUser().getUid();
                                            DB.CapNhatUser(mAuth.getCurrentUser().getUid());
                                            // hide the progress bar
                                            // if sign-in is successful
                                            // intent to home activity
                                            Intent intent
                                                    = new Intent(LoginActivity.this,
                                                    MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                    "Wrong Email or Password!!",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                });

            }
        });
        tvDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        tvforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnDangnhap = (Button) findViewById(R.id.buttonDangnhap);
        tvDangky = (TextView) findViewById(R.id.textView_register);
        tvforgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
        edttaikhoan = (EditText) findViewById(R.id.editTextUser);
        edtmatkhau = (EditText) findViewById(R.id.editTextPass);
    }
}