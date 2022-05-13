package com.example.aoneenglish.account;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.example.aoneenglish.ui.home.Database;

public class Infor_Acc_Activity extends AppCompatActivity {

    final String DATABASE_NAME = "HocTiengAnh.db";
    DatabaseAccess DB;
    SQLiteDatabase database;
    EditText tvHoten, tvEmail, tvSdt, tvUID;
    TextView tvtaikhoan, tvTen, tvPoint;
    Button btnCapNhat;
    String iduser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_acc);
        DB = DatabaseAccess.getInstance(getApplicationContext());
        AnhXa();
        iduser = DB.iduser;
        LayUser();

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapNhatThongTin();
            }
        });

    }

    private void AnhXa() {
        tvHoten = findViewById(R.id.textIntEdtHoten);
        tvEmail = findViewById(R.id.textIntEdtEmail);
        tvSdt = findViewById(R.id.textIntEdtSdt);
        tvUID = findViewById(R.id.textIntEdtUID);
        tvtaikhoan = findViewById(R.id.tVusername);
        tvTen = findViewById(R.id.textViewTen);
        tvPoint = findViewById(R.id.textviewPoint);
        btnCapNhat = findViewById(R.id.buttonCapNhat);

        tvUID.setEnabled(false);
        tvEmail.setEnabled(false);

    }

    private void CapNhatThongTin() {
        String hoten = tvHoten.getText().toString();
        String sdt = tvSdt.getText().toString();
        if (hoten == "" || sdt == "") {
            Toast.makeText(this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            Boolean checkupdate = DB.capnhatthongtin(DB.iduser, hoten, sdt);
            if (checkupdate == true) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }

    }
    
    private void TruyenThongTin() {
        //Truyền thông tin
        tvHoten.setText(user.getHoTen());
        tvTen.setText(user.getHoTen());
        tvtaikhoan.setText(user.getEmail());
        tvPoint.setText(String.valueOf(user.getPoint()));
        tvEmail.setText(user.getEmail());
        tvSdt.setText(user.getSDT());
        tvUID.setText(user.getIduser());

    }

    public void LayUser() {
        database = Database.initDatabase(Infor_Acc_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?", new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser, HoTen, Point, Email, SDT);

        TruyenThongTin();

    }

}