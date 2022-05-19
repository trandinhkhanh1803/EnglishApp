package com.example.aoneenglish.Learning_vocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.example.aoneenglish.ui.home.Database;

import java.util.ArrayList;

public class List_Voca_Activity extends AppCompatActivity {

    final String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    ImageView imgback;

    GridView dstuvungs;
    Button Ontap;
    ArrayList<Vocabulary> DStuvung;
    ArrayList<String> listtu;
    List_Voca_Adapter adapter;

    int idbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
// nhan tu vung tu Learning_Voca_Activity
        Intent intent = getIntent();
        idbo = intent.getIntExtra("idbo", 0);

        dstuvungs = (GridView) findViewById(R.id.lgvTuVung);
        Ontap = (Button) findViewById(R.id.btnOnTap);
        imgback = findViewById(R.id.imgVBackDSTV);
        DStuvung = new ArrayList<>();
        listtu = new ArrayList<>();
        AddArrayTV();
        adapter = new List_Voca_Adapter(List_Voca_Activity.this, R.layout.row_vocabulary_list, DStuvung);
        dstuvungs.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(List_Voca_Activity.this,
                        Learning_Voca_Activity.class);
                startActivity(intent);
            }
        });

        Ontap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ontap = new Intent(List_Voca_Activity.this, Word_Matters_Activity.class);
                ontap.putExtra("idbo", idbo);
                startActivity(ontap);
            }
        });


    }
// them tu vung
    private void AddArrayTV() {
        database = Database.initDatabase(List_Voca_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TuVung WHERE ID_Bo = ?", new String[]{String.valueOf(idbo)});
        DStuvung.clear();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idtu = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String dapan = cursor.getString(2);
            String dichnghia = cursor.getString(3);
            String loaitu = cursor.getString(4);
            String audio = cursor.getString(5);
            byte[] anh = cursor.getBlob(6);

            DStuvung.add(new Vocabulary(idtu, idbo, dapan, dichnghia, loaitu, audio, anh));
        }
    }
}