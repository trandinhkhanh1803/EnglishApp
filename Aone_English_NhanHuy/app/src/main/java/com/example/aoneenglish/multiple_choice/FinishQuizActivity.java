package com.example.aoneenglish.multiple_choice;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.MainActivity;
import com.example.aoneenglish.R;

public class FinishQuizActivity extends AppCompatActivity {

    TextView txtcongrats,txtfinalqtrue,txtfinaltext, txtfinalScore;
    Button btnReturn;
    ImageView imgtrophy;
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    int score;
    int questiontrue;
    int qcount;
    Animation smalltobig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_learn);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        Intent intent=getIntent();
        score=intent.getIntExtra("score",0);
        questiontrue = intent.getIntExtra("questiontrue",0);
        qcount = intent.getIntExtra("qcount",0);

        Anhxa();


        if(questiontrue==4){
            txtfinalqtrue.setText(questiontrue + " / " + qcount);
            txtcongrats.setText("Your final result: ");
            txtfinaltext.setText("Almost there!!");
            txtfinalScore.setText(" "+score);
        }
        if(questiontrue<4){
            txtfinalqtrue.setText(questiontrue +" / "+ qcount);
            txtcongrats.setText("Your final result: ");
            txtfinaltext.setText("Good luck next time !!");
            txtfinalScore.setText(" "+score);
        }
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishQuizActivity.this, MainActivity.class));
            }
        });

    }
    public void Anhxa(){
        txtfinalScore=findViewById(R.id.tvPoints);
        txtcongrats=findViewById(R.id.txtcongrats);
        txtfinalqtrue=findViewById(R.id.txtquestiontrue);
        txtfinaltext=findViewById(R.id.txtfinaltext);
        btnReturn=findViewById(R.id.btnReturn);
        imgtrophy=findViewById(R.id.imgtrophy);
        imgtrophy.startAnimation(smalltobig);
    }
}