package com.example.aoneenglish.Listening;

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

public class Listeing_test_Activity extends AppCompatActivity {
    ListView listView;
    ImageView imgback;
    ArrayList<learn_Set> boCauHoiArrayList;
    learn_set_Adapter boCauHoiAdapter;
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    int idbocauhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        listView= findViewById(R.id.lvluyennghe);
        imgback = findViewById(R.id.imgVBackLN);
        boCauHoiArrayList=new ArrayList<>();
        AddArrayBTN();
        boCauHoiAdapter=new learn_set_Adapter(Listeing_test_Activity.this,R.layout.row_listening_set,boCauHoiArrayList);
        listView.setAdapter(boCauHoiAdapter);
        boCauHoiAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database= Database.initDatabase(Listeing_test_Activity.this,DATABASE_NAME);
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
                Intent quiz= new Intent(Listeing_test_Activity.this, Listening_Activity.class);
                quiz.putExtra("Bo",idbocauhoi);
                startActivity(quiz);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Listeing_test_Activity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AddArrayBTN(){
        database= Database.initDatabase(Listeing_test_Activity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
        boCauHoiArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            boCauHoiArrayList.add(new learn_Set(idbo,stt,tenbo));

        }
    }
}
