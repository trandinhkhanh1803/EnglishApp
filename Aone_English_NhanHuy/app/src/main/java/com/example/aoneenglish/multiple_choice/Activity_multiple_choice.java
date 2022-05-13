package com.example.aoneenglish.multiple_choice;

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

public class Activity_multiple_choice extends AppCompatActivity {

    ListView listView;
    ArrayList<learn_Set> learnSetArrayList;
    learn_set_Adapter learnsetAdapter;
    ImageView imgback;
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    int idbocauhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        listView= findViewById(R.id.lvtracnghiem);
        imgback = findViewById(R.id.imgVBackTN);
        learnSetArrayList =new ArrayList<>();
        AddArrayBTN();
        learnsetAdapter =new learn_set_Adapter(Activity_multiple_choice.this, R.layout.row_test_set, learnSetArrayList);
        listView.setAdapter(learnsetAdapter);
        learnsetAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database= Database.initDatabase(Activity_multiple_choice.this,DATABASE_NAME);
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
                Intent quiz= new Intent(Activity_multiple_choice.this,QuizActivity.class);
                quiz.putExtra("Bo",idbocauhoi);

                startActivity(quiz);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Activity_multiple_choice.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AddArrayBTN(){
        database= Database.initDatabase(Activity_multiple_choice.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
        learnSetArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            learnSetArrayList.add(new learn_Set(idbo,stt,tenbo));

        }
    }
}