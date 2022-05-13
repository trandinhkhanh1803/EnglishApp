
package com.example.aoneenglish.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextView tvDangNhap;
    EditText edtHoTen, edtEmail, edtSdt, edtMatKhau, edtXacNhan;
    Button btnSignUp;
    FirebaseAuth mAuth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseAccess DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AnhXa();
        mAuth = FirebaseAuth.getInstance();

        DB = DatabaseAccess.getInstance(getApplicationContext());

        tvDangNhap = (TextView) findViewById(R.id.textView_login);

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtHoTen.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();
                String xacnhanmatkhau = edtXacNhan.getText().toString().trim();

                if (hoten.equals("") || email.equals("") || sdt.equals("") || matkhau.equals("")) {
                    Toast.makeText(Signup.this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhau.equals(xacnhanmatkhau)) {
                        Boolean kiemtrataikhoan = DB.checktaikhoan(email);
                        if (kiemtrataikhoan == false) {
                            mAuth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DB.open();
                                        Boolean insert = DB.insertData(mAuth.getCurrentUser().getUid(), hoten, email, sdt, 0);
                                        DB.close();
                                        btnSignUp.setText(insert.toString());
                                        Toast.makeText(Signup.this, "Signup successfully ", Toast.LENGTH_SHORT).show();
                                        // if the user created intent to login activity
                                        rootNode = FirebaseDatabase.getInstance();
                                        reference = rootNode.getReference("User");
                                        User newuser = new User(mAuth.getCurrentUser().getUid(), hoten, 0, email, sdt);
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newuser);

                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {

                                        Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                            if(insert == true)
//                            {
//                                btnSignUp.setText(insert.toString());
//                                Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                                startActivity(intent);
//                            }
//                            else{
//                                Toast.makeText(Signup.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            Toast.makeText(Signup.this, "Account already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                        edtMatKhau.setText("");
                        edtXacNhan.setText("");
                    }
                }
            }
        });
    }

    private void AnhXa() {
        tvDangNhap = (TextView) findViewById(R.id.textView_login);
        edtHoTen = (EditText) findViewById(R.id.editTextEmailNav);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtSdt = (EditText) findViewById(R.id.editTextSdt);
        edtMatKhau = (EditText) findViewById(R.id.editTextMatKhau);
        edtXacNhan = (EditText) findViewById(R.id.editTextXacNhan);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
    }
}