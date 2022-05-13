package com.example.aoneenglish.Fill_in_the_word;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.example.aoneenglish.account.DatabaseAccess;
import com.example.aoneenglish.account.User;
import com.example.aoneenglish.ui.home.Database;

public class Fill_Blanks_Activity extends AppCompatActivity {

    final String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    DatabaseAccess DB;
    TextView txtscoreDK, txtquestcountDK, txtquestionDK, txttimeDK, txtGoiy;
    EditText edtAnswerDK;
    Button btnconfirm, btnQuit;
    int questioncurrent = 0;
    int questiontrue = 0;
    String answer;
    int score = 0;
    int idbo;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);
        DB = DatabaseAccess.getInstance(getApplicationContext());

        Anhxa();
        LayUser();

        Intent intent = getIntent();
        idbo = intent.getIntExtra("BoDK", 0);
        txttimeDK.setText(" ");
        shownextquestion(questioncurrent);


        CountDownTimer countDownTimer = new CountDownTimer(3000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                questioncurrent++;
                edtAnswerDK.setTextColor(Color.BLACK);
                btnconfirm.setEnabled(true);
                edtAnswerDK.setText("");
                answer = "";
                shownextquestion(questioncurrent);
            }
        };


        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                showanswer();

                countDownTimer.start();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Fill_Blanks_Activity.this,
                        Fill_in_the_word_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void LayUser() {
        database = Database.initDatabase(Fill_Blanks_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?", new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser, HoTen, Point, Email, SDT);
    }

    public void shownextquestion(int pos) {

        database = Database.initDatabase(Fill_Blanks_Activity.this, DATABASE_NAME);
        String a = null;
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Bo=?", new String[]{String.valueOf(idbo)});

        txtquestcountDK.setText("Question: " + (questioncurrent + 1) + "/" + cursor.getCount() + "");

        edtAnswerDK.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));


        if (pos == cursor.getCount()) {
            DB.capnhatdiem(DB.iduser, user.getPoint(), score);
            Intent intent = new Intent(Fill_Blanks_Activity.this, Finish_DK_Activity.class);
            intent.putExtra("scoreDK", score);
            intent.putExtra("questiontrueDK", questiontrue);
            intent.putExtra("qcountDK", pos);
            startActivity(intent);
        }
        for (int i = pos; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String questcontent = cursor.getString(2);
            answer = cursor.getString(3);
            txtGoiy.setText(cursor.getString(4));
            txtquestionDK.setText(questcontent);
            break;
        }
    }

    public void showanswer() {
        edtAnswerDK.setText(answer);
        edtAnswerDK.setTextColor(Color.GREEN);
        edtAnswerDK.clearFocus();
    }

    public void checkAnswer() {
        btnconfirm.setEnabled(false);
        if (answer.equals(edtAnswerDK.getText().toString())) {
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
            edtAnswerDK.setTextColor(Color.GREEN);
            questiontrue++;
            score += 5;
            txtscoreDK.setText("Score: " + score);
            edtAnswerDK.clearFocus();

        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            edtAnswerDK.setTextColor(Color.RED);
            edtAnswerDK.startAnimation(shakeError());
            edtAnswerDK.clearFocus();
        }

    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public void Anhxa() {
        txtscoreDK = findViewById(R.id.txtscoreDK);
        txtquestcountDK = findViewById(R.id.txtquestcountDK);
        txtquestionDK = findViewById(R.id.txtquestionDK);
        txttimeDK = findViewById(R.id.txttimeDK);
        edtAnswerDK = findViewById(R.id.AnswerDK);
        btnconfirm = findViewById(R.id.btnconfirmDK);
        txtGoiy = findViewById(R.id.textviewGoiy);
        btnQuit = findViewById(R.id.btnQuitDK);
    }

}