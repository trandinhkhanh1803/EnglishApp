package com.example.aoneenglish.Fill_in_the_word;

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

public class Fill_in_the_word_Activity extends AppCompatActivity {

    ListView listView;
    ImageView imgback;
    ArrayList<learn_Set> boHocTapArrayList;
    learn_set_Adapter boHocTapAdapter;
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    int idbocauhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_word);

        listView= findViewById(R.id.lvdienkhuyet);
        imgback = findViewById(R.id.imgVBackDK);
        boHocTapArrayList =new ArrayList<>();
        AddArrayBTN();
        boHocTapAdapter =new learn_set_Adapter(Fill_in_the_word_Activity.this,R.layout.row_fill_in_the_word_set, boHocTapArrayList);
        listView.setAdapter(boHocTapAdapter);
        boHocTapAdapter.notifyDataSetChanged();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Fill_in_the_word_Activity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database= Database.initDatabase(Fill_in_the_word_Activity.this,DATABASE_NAME);
                String a=null;
                Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
                for(int i=position;i<cursor.getCount();i++){
                    cursor.moveToPosition(i);
                    int idbo=cursor.getInt(0);
                    int stt=cursor.getInt(1);
                    String tenbo=cursor.getString(2);
                    a=tenbo;
                    idbocauhoi=idbo;
                    break;
                }
                Intent quiz= new Intent(Fill_in_the_word_Activity.this, Fill_Blanks_Activity.class);
                quiz.putExtra("BoDK",idbocauhoi);

                startActivity(quiz);
            }
        });
    }
    private void AddArrayBTN(){
        database= Database.initDatabase(Fill_in_the_word_Activity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
        boHocTapArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            boHocTapArrayList.add(new learn_Set(idbo,stt,tenbo));

        }
    }
}