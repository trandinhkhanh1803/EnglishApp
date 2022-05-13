package com.example.aoneenglish.Listening;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.R;
import com.example.aoneenglish.account.DatabaseAccess;
import com.example.aoneenglish.account.User;
import com.example.aoneenglish.ui.home.Database;

import java.util.ArrayList;

public class Listening_Activity extends AppCompatActivity {
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    DatabaseAccess DB;
    TextView txtscore,txtquestcount;
    RadioGroup rdgchoices;
    RadioButton btnop1,btnop2,btnop3,btnop4;
    Button btnconfirm;
    Button btnquit;
    ImageView imHA;
    private ArrayList<Listening_Practice> listeningPractices;
    private MediaPlayer mediaPlayer;
    private ImageButton ImgBT;

    User user;

    String URL = "https://github.com/quanghuyK20/Listening_MP3/blob/master/Listening/ve-nghe-me-ru.mp3?raw=true";
    int questioncurrent = 0;
    int questiontrue = 0;
    int answer=0;
    int score=0;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_listening);
        DB = DatabaseAccess.getInstance(getApplicationContext());
        Anhxa();
        LayUser();
        Intent intent=getIntent();
        idbo=intent.getIntExtra("Bo",0);
        listeningPractices = new ArrayList<>();
        AddArrayCLN();

        shownextquestion(questioncurrent, listeningPractices);

        // Create MediaPlayer.
        mediaPlayer=  new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {mp.start();}
        });
        CountDownTimer countDownTimer=new CountDownTimer(3000,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                showanswer();
            }

            @Override
            public void onFinish() {

                btnconfirm.setEnabled(true);
                shownextquestion(questioncurrent, listeningPractices);
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


        // When the video file ready for playback.
        this.ImgBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // http://example.coom/mysong.mp3
                //String mediaURL = MediaPlayerUtils.URL_MEDIA_SAMPLE;
                String mediaURL = URL;
                Media_Player.playURLMedia(Listening_Activity.this, mediaPlayer, mediaURL);
                //doStart();
            }
        });

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                doStop();
                Intent intent
                        = new Intent(Listening_Activity.this,
                        Listeing_test_Activity.class);
                startActivity(intent);
            }
        });
    }
    public void Anhxa(){
        txtscore= findViewById(R.id.txtscoreLN);
        txtquestcount= findViewById(R.id.txtquestcountLN);
        rdgchoices= findViewById(R.id.radiochoices);
        btnop1=findViewById(R.id.op1);
        btnop2=findViewById(R.id.op2);
        btnop3=findViewById(R.id.op3);
        btnop4=findViewById(R.id.op4);
        btnconfirm=findViewById(R.id.btnconfirmLN);
        btnquit=findViewById(R.id.btnQuitLN);
        imHA=findViewById(R.id.imgHinh);
        ImgBT=findViewById(R.id.ImgBT);
    }

    private void AddArrayCLN(){
        database = Database.initDatabase(Listening_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LuyenNghe WHERE ID_Bo = ?",new String[]{String.valueOf(idbo)});
        listeningPractices.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbai = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String A = cursor.getString(2);
            String B = cursor.getString(3);
            String C = cursor.getString(4);
            String D = cursor.getString(5);
            String True = cursor.getString(6);
            byte[] hinh = cursor.getBlob(7);
            String audio = cursor.getString(8);

            listeningPractices.add(new Listening_Practice(idbai,idbo,A,B,C,D,True,hinh,audio));
        }
    }

    public void LayUser()
    {
        database = Database.initDatabase(Listening_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser,HoTen,Point,Email,SDT);
    }

    public void shownextquestion(int pos,  ArrayList<Listening_Practice> listeningPractices){

        if(pos > 0 ) doStop();
//        database= Database.initDatabase(ListeningActivity.this,DATABASE_NAME);
//        String a=null;
//        Cursor cursor=database.rawQuery("SELECT * FROM LuyenNghe WHERE ID_Bo=?",new String[]{String.valueOf(idbo)});
//        txtquestcount.setText("Question: "+(questioncurrent+1)+"/"+cursor.getCount()+"");
        txtquestcount.setText("Question: "+(questioncurrent+1)+"/"+ listeningPractices.size()+"");
        rdgchoices.clearCheck();
        btnop1.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop2.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop3.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop4.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
//        if(pos==cursor.getCount()){
        if(pos== listeningPractices.size()){
            DB.capnhatdiem(DB.iduser,user.getPoint(),score);
            Intent intent=new Intent(Listening_Activity.this, Finish_Quiz_LS_Activity.class);
            intent.putExtra("score",score);
            intent.putExtra("questiontrue", questiontrue);
            intent.putExtra("qcount", pos);
            startActivity(intent);
        }
        else {
            byte[] anh = listeningPractices.get(pos).getHinhanh();
            Bitmap img= BitmapFactory.decodeByteArray(anh,0,anh.length);
            imHA.setImageBitmap(img);

            String URLaudio = listeningPractices.get(pos).getAudio();
            URL = URLaudio;

            answer=Integer.valueOf(listeningPractices.get(pos).getDapanTrue());
            btnop1.setText(listeningPractices.get(pos).getDapanA());
            btnop2.setText(listeningPractices.get(pos).getDapanB());
            btnop3.setText(listeningPractices.get(pos).getDapanC());
            btnop4.setText(listeningPractices.get(pos).getDapanD());
        }

    }
    public void checkans(){
        btnconfirm.setEnabled(false);
        if(btnop1.isChecked()){
            if(1==answer) {

                score+=5;
                questiontrue++;
            }
        }
        if(btnop2.isChecked()){
            if(2==answer) {

                score+=5;
                questiontrue++;
            }
        }
        if(btnop3.isChecked()){
            if(3==answer) {

                score+=5;
                questiontrue++;
            }
        }
        if(btnop4.isChecked()){
            if(4==answer) {

                score+=5;
                questiontrue++;
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
    private void doStart( )  {
        if(this.mediaPlayer.isPlaying()) {
            //this.mediaPlayer.stop();
            this.mediaPlayer.pause();
            this.mediaPlayer.reset();
        }
        else {this.mediaPlayer.start();}
    }
    private void doStop()  {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
    }
}
