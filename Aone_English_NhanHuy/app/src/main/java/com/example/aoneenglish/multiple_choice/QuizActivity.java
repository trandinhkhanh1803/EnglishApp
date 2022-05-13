package com.example.aoneenglish.multiple_choice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.example.aoneenglish.account.DatabaseAccess;
import com.example.aoneenglish.account.User;
import com.example.aoneenglish.ui.home.Database;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    DatabaseAccess DB;
    TextView txtscore,txtquestcount,txtquestion,txttime;
    RadioGroup rdgchoices;
    RadioButton btnop1,btnop2,btnop3,btnop4;
    Button btnconfirm;
    Button btnquit;
    private ArrayList<Multiple_choice_questions> multiplechoicequestions;
    int questioncurrent = 0;
    int questiontrue = 0;
    int answer=0;
    int score=0;
    int idbo;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        DB = DatabaseAccess.getInstance(getApplicationContext());
        Anhxa();

        LayUser();
        Intent intent=getIntent();
        idbo=intent.getIntExtra("Bo",0);
        txttime.setText(" ");

        multiplechoicequestions = new ArrayList<>();
        AddArrayCTN();
        shownextquestion(questioncurrent, multiplechoicequestions);

        CountDownTimer countDownTimer=new CountDownTimer(3000,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                showanswer();
            }

            @Override
            public void onFinish() {

                btnconfirm.setEnabled(true);
                shownextquestion(questioncurrent, multiplechoicequestions);
            }
        };
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkans();
                questioncurrent++;
                countDownTimer.start();
            }
        });

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(QuizActivity.this,
                        Activity_multiple_choice.class);
                startActivity(intent);
            }
        });
    }
    public void Anhxa(){
        txtscore= findViewById(R.id.txtscoreTN);
        txtquestcount= findViewById(R.id.txtquestcountTN);
        txtquestion= findViewById(R.id.txtquestionTN);
        txttime= findViewById(R.id.txttimeTN);
        rdgchoices= findViewById(R.id.radiochoices);
        btnop1=findViewById(R.id.op1);
        btnop2=findViewById(R.id.op2);
        btnop3=findViewById(R.id.op3);
        btnop4=findViewById(R.id.op4);
        btnconfirm=findViewById(R.id.btnconfirmTN);
        btnquit=findViewById(R.id.btnQuitTN);
    }

    private void AddArrayCTN(){
        database = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Bo = ?",new String[]{String.valueOf(idbo)});
        multiplechoicequestions.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idcau = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String noidung = cursor.getString(2);
            String A = cursor.getString(3);
            String B = cursor.getString(4);
            String C = cursor.getString(5);
            String D = cursor.getString(6);
            String True = cursor.getString(7);

            multiplechoicequestions.add(new Multiple_choice_questions(idcau,idbo,noidung,A,B,C,D,True));
        }
    }

    public void LayUser()
    {
        database = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser,HoTen,Point,Email,SDT);
    }

    public void shownextquestion(int pos, ArrayList<Multiple_choice_questions> multiplechoicequestions){

//        database= Database.initDatabase(QuizActivity.this,DATABASE_NAME);
//        String a=null;
//        Cursor cursor=database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Bo=?",new String[]{String.valueOf(idbo)});
//        txtquestcount.setText("Question: "+(questioncurrent+1)+"/"+cursor.getCount()+"");
        txtquestcount.setText("Question: "+(questioncurrent+1)+"/"+ multiplechoicequestions.size()+"");
        rdgchoices.clearCheck();
        btnop1.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop2.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop3.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop4.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
//        if(pos==cursor.getCount()){
        if(pos== multiplechoicequestions.size()){
            DB.capnhatdiem(DB.iduser,user.getPoint(),score);
            Intent intent=new Intent(QuizActivity.this,FinishQuizActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("questiontrue", questiontrue);
            intent.putExtra("qcount", pos);
            startActivity(intent);
        }
        else {
            answer=Integer.valueOf(multiplechoicequestions.get(pos).getDapanTrue());
            txtquestion.setText(multiplechoicequestions.get(pos).getNoidung());
            btnop1.setText(multiplechoicequestions.get(pos).getDapanA());
            btnop2.setText(multiplechoicequestions.get(pos).getDapanB());
            btnop3.setText(multiplechoicequestions.get(pos).getDapanC());
            btnop4.setText(multiplechoicequestions.get(pos).getDapanD());
        }

    }
    public void checkans(){
        btnconfirm.setEnabled(false);
        if(btnop1.isChecked()){
            if(1==answer) {
                questiontrue++;
                score+=5;
            }
        }
        if(btnop2.isChecked()){
            if(2==answer) {
                questiontrue++;
                score+=5;
            }
        }
        if(btnop3.isChecked()){
            if(3==answer) {
                questiontrue++;
                score+=5;
            }
        }
        if(btnop4.isChecked()){
            if(4==answer) {
                questiontrue++;
                score+=5;
            }
        }

        txtscore.setText("Score: "+score+"");
    }
    public void showanswer(){
        if(1==answer) {
            btnop1.setBackground(this.getResources().getDrawable(R.drawable.button_2));
            btnop2.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop3.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop4.setBackground(this.getResources().getDrawable(R.drawable.button_1));

        }
        if(2==answer) {
            btnop1.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop2.setBackground(this.getResources().getDrawable(R.drawable.button_2));
            btnop3.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop4.setBackground(this.getResources().getDrawable(R.drawable.button_1));

        }
        if(3==answer) {
            btnop1.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop2.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop3.setBackground(this.getResources().getDrawable(R.drawable.button_2));
            btnop4.setBackground(this.getResources().getDrawable(R.drawable.button_1));

        }
        if(4==answer) {
            btnop1.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop2.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop3.setBackground(this.getResources().getDrawable(R.drawable.button_1));
            btnop4.setBackground(this.getResources().getDrawable(R.drawable.button_2));

        }
    }
}