package com.example.aoneenglish.Learning_vocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.MainActivity;
import com.example.aoneenglish.R;
import com.example.aoneenglish.Learning_set.learn_Set;
import com.example.aoneenglish.Learning_set.learn_set_Adapter;
import com.example.aoneenglish.ui.home.Database;

import java.util.ArrayList;

public class Learning_Voca_Activity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    ImageView imgback;

    ArrayList<learn_Set> boTuVungs;
    learn_set_Adapter adapter;
    ListView botuvungs;

    int idbo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_voca);
        botuvungs = findViewById(R.id.listviewHTV);
        imgback = findViewById(R.id.imgVBackHTV);
        boTuVungs = new ArrayList<>();
        AddArrayBTV();
        adapter = new learn_set_Adapter(Learning_Voca_Activity.this,R.layout.row_voca_set,boTuVungs);
        botuvungs.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        botuvungs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idbo = boTuVungs.get(position).getIdBo();
                Intent dstv = new Intent(Learning_Voca_Activity.this, List_Voca_Activity.class);
                dstv.putExtra("idbo", idbo);
                startActivity(dstv);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Learning_Voca_Activity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddArrayBTV(){
        database = Database.initDatabase(Learning_Voca_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi",null);
        boTuVungs.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            boTuVungs.add(new learn_Set(idbo,stt,tenbo));
        }

    }
}