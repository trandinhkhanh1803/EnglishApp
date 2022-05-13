package com.example.aoneenglish.Learning_vocabulary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoneenglish.Listening.Media_Player;
import com.example.aoneenglish.R;
import com.example.aoneenglish.account.DatabaseAccess;
import com.example.aoneenglish.account.User;
import com.example.aoneenglish.ui.home.Database;

import java.util.ArrayList;
import java.util.Random;

public class Word_Matters_Activity extends AppCompatActivity {


    final String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    DatabaseAccess DB;

    private int presCounter = 0;
    private String[] keys = {"a", "b", "c", "d", "e", "f", "g",
            //"h", "i", "j", "k", "l", "m", "n",
            //"o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z"};

    private MediaPlayer mediaPlayer;
    String URL = "https://github.com/quanghuyK20/Listening_MP3/blob/master/Listening/ve-nghe-me-ru.mp3";
    String UL = "hello";

    private String textAnswer = "AOne English VKU";
    private int maxPresCounter = 0;
    private TextView textScreen, textQuestion, textTitle;
    private TextView tvWordCount, tvScore;
    private ImageButton ListenTV;
    private ImageView imgview;
    private Button btnquit;
    private ArrayList<Vocabulary> DStuvung;
    private Animation smallbigforth;
    private int idbo;
    private int score = 0;
    private int dem;
    private int tu = 1;
    User user;
    Animation smalltobig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_matters);
        smalltobig = AnimationUtils.loadAnimation(Word_Matters_Activity.this, R.anim.smalltobig);

        DB = DatabaseAccess.getInstance(getApplicationContext());
        AnhXa();
        LayUser();

        Intent intent = getIntent();
        idbo = intent.getIntExtra("idbo", 0);
        DStuvung = new ArrayList<>();

        AddArrayTV();

        // Create MediaPlayer.
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        Bitmap img = BitmapFactory.decodeByteArray(DStuvung.get(0).getAnh(), 0, DStuvung.get(0).getAnh().length);
        imgview.setImageBitmap(img);
        imgview.startAnimation(smalltobig);
        textQuestion.setText("(" + DStuvung.get(0).getLoaitu() + ") - " + "(" + DStuvung.get(0).getDichnghia() + ")");
        tvWordCount.setText("Word: " + tu + "/" + DStuvung.size());
        tvScore.setText("Score: " + score);

        textAnswer = DStuvung.get(0).getDapan();
        URL = DStuvung.get(0).getAudio();


        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        for (int i = 0; i < textAnswer.length(); i++) {
            keys[i] = String.valueOf(textAnswer.charAt(i));
        }

        keys = shuffleArray(keys);

        dem = 0;
        while (dem < keys.length) {
            if (dem < 4) {
                addView(((LinearLayout) findViewById(R.id.layoutParent1)), keys[dem], ((EditText) findViewById(R.id.editText)));
            } else if (dem < 8) {
                addView(((LinearLayout) findViewById(R.id.layoutParent2)), keys[dem], ((EditText) findViewById(R.id.editText)));
            } else
                addView(((LinearLayout) findViewById(R.id.layoutParent3)), keys[dem], ((EditText) findViewById(R.id.editText)));
            dem++;
        }

        // When the video file ready for playback.
        ListenTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // http://example.coom/mysong.mp3
                //String mediaURL = MediaPlayerUtils.URL_MEDIA_SAMPLE;
                doStop();
                if (UL == URL)
                    Media_Player.playURLMedia(Word_Matters_Activity.this, mediaPlayer, UL);
                else {
                    String mediaURL = URL;
                    UL = URL;
                    Media_Player.playURLMedia(Word_Matters_Activity.this, mediaPlayer, mediaURL);
                }
            }
        });

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent
                        = new Intent(Word_Matters_Activity.this,
                        Learning_Voca_Activity.class);
                startActivity(intent);
            }
        });


        maxPresCounter = textAnswer.length();
    }

    private void AnhXa() {
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        imgview = (ImageView) findViewById(R.id.imgview);
        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView) findViewById(R.id.textTitle);
        tvWordCount = (TextView) findViewById(R.id.tvWord);
        tvScore = (TextView) findViewById(R.id.tvScore);
        ListenTV = (ImageButton) findViewById(R.id.ListenTuVung);
        btnquit = (Button) findViewById(R.id.btnQuitHTV);
    }

    public void LayUser() {
        database = Database.initDatabase(Word_Matters_Activity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?", new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser, HoTen, Point, Email, SDT);
    }

    private void AddArrayTV() {
        database = Database.initDatabase(Word_Matters_Activity.this, DATABASE_NAME);
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


    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(35);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);
        tvScore.setTypeface(typeface);
        tvWordCount.setTypeface(typeface);

        textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;
                    textView.setClickable(false);

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });
        viewParent.addView(textView);
    }


    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout1 = findViewById(R.id.layoutParent1);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);
        LinearLayout linearLayout3 = findViewById(R.id.layoutParent3);

        if (editText.getText().toString().equals(textAnswer)) {
            if (tu == DStuvung.size()) {
                score += 5;
                DB.capnhatdiem(DB.iduser, user.getPoint(), score);
                Toast.makeText(Word_Matters_Activity.this, "Hoàn Thành Xuất Sắc!!~(^.^)~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Word_Matters_Activity.this, Finish_Learning_VC_Activity.class);
                intent.putExtra("score", score);
                intent.putExtra("questiontrue", tu);
                intent.putExtra("qcount", DStuvung.size());
                startActivity(intent);
                finish();
            } else {
                if (tu > 0) doStop();
                Toast.makeText(Word_Matters_Activity.this, "Chính xác!!(^.^)", Toast.LENGTH_SHORT).show();
                Bitmap img = BitmapFactory.decodeByteArray(DStuvung.get(tu).getAnh(), 0, DStuvung.get(tu).getAnh().length);
                imgview.setImageBitmap(img);
                imgview.startAnimation(smalltobig);
                textQuestion.setText("(" + DStuvung.get(tu).getLoaitu() + ") - " + "(" + DStuvung.get(tu).getDichnghia() + ")");
                textAnswer = DStuvung.get(tu).getDapan();
                URL = DStuvung.get(tu).getAudio();
                for (int i = 0; i < textAnswer.length(); i++) {
                    keys[i] = String.valueOf(textAnswer.charAt(i));
                }
                maxPresCounter = textAnswer.length();
                editText.setText("");
                score += 5;
                tu++;
                tvWordCount.setText("Word: " + tu + "/" + DStuvung.size());
                tvScore.setText("Score: " + score);
            }

        } else {
            Toast.makeText(Word_Matters_Activity.this, "Sai rồi!!(T.T)", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

        keys = shuffleArray(keys);
        linearLayout1.removeAllViews();
        linearLayout2.removeAllViews();
        linearLayout3.removeAllViews();

        dem = 0;
        while (dem < keys.length) {
            if (dem < 4) {
                addView(linearLayout1, keys[dem], editText);
            } else if (dem < 8) {
                addView(linearLayout2, keys[dem], editText);
            } else addView(linearLayout3, keys[dem], editText);
            dem++;
        }
    }

    private void doStop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
    }

}