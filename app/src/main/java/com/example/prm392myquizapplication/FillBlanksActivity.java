package com.example.prm392myquizapplication;

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

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.dao.DatabaseAccess;
import com.example.prm392myquizapplication.data.BlankSentence;
import com.example.prm392myquizapplication.data.OnlineUser;

import java.util.ArrayList;



public class FillBlanksActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    DatabaseAccess DB;
    TextView txtscoreDK,txtquestcountDK,txtquestionDK,txttimeDK,txtGoiy;
    EditText edtAnswerDK;
    Button btnconfirm, btnQuit;
    private ArrayList<BlankSentence> blankSentences;
    int questioncurrent = 0;
    int questiontrue = 0;
    String answer;
    int score=0;
    int idbo;

    OnlineUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);
        DB = DatabaseAccess.getInstance(getApplicationContext());

        Anhxa();
        LayUser();

        Intent intent=getIntent();
        idbo=intent.getIntExtra("BoDK",0);
        txttimeDK.setText(" ");

        blankSentences = new ArrayList<>();
        AddArrayCDK();

        if(blankSentences.size() <= 0) {
            Toast.makeText(FillBlanksActivity.this, "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!", Toast.LENGTH_LONG).show();
            Intent error = new Intent(FillBlanksActivity.this, BlankSentenceActivity.class);
            finish();
            startActivity(error);
        }
        else {
            shownextquestion(questioncurrent, blankSentences);


            CountDownTimer countDownTimer=new CountDownTimer(3000,2000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    questioncurrent++;
                    edtAnswerDK.setTextColor(Color.BLACK);
                    btnconfirm.setEnabled(true);
                    edtAnswerDK.setText("");
                    answer="";
                    shownextquestion(questioncurrent, blankSentences);
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
                            = new Intent(FillBlanksActivity.this,
                            BlankSentenceActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
    public void LayUser()
    {
        database = Database.initDatabase(FillBlanksActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        String Email = cursor.getString(2);
        String SDT = cursor.getString(3);
        user = new OnlineUser(Iduser,HoTen,Email,SDT);
    }

    private void AddArrayCDK(){
        database = Database.initDatabase(FillBlanksActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Bo = ?",new String[]{String.valueOf(idbo)});
        blankSentences.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idcau = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String noidung = cursor.getString(2);
            String dapan = cursor.getString(3);
            String goiy = cursor.getString(4);

            blankSentences.add(new BlankSentence(idcau,idbo,noidung,dapan,goiy));
        }
    }

    public void shownextquestion(int pos, ArrayList<BlankSentence> blankSentences) {
        txtquestcountDK.setText("Question: " + (questioncurrent + 1) + "/" + blankSentences.size());
        edtAnswerDK.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));


//        if (pos == cursor.getCount()) {
        if (pos == blankSentences.size()) {
            Intent intent = new Intent(FillBlanksActivity.this, FinishDKActivity.class);
            intent.putExtra("scoreDK", score);
            intent.putExtra("questiontrueDK", questiontrue);
            intent.putExtra("qcountDK", pos);
            startActivity(intent);
        }
        else {
            answer = blankSentences.get(pos).getDapan();
            txtGoiy.setText(blankSentences.get(pos).getGoiy());
            txtquestionDK.setText(blankSentences.get(pos).getNoidung());
        }

    }
    public void showanswer(){
        edtAnswerDK.setText(answer);
        edtAnswerDK.setTextColor(Color.GREEN);
        edtAnswerDK.clearFocus();
    }
    public void checkAnswer()
    {
        btnconfirm.setEnabled(false);
        if(answer.equals(edtAnswerDK.getText().toString()))

        {
            Toast.makeText(this, "Đáp án chính xác", Toast.LENGTH_SHORT).show();
            edtAnswerDK.setTextColor(Color.GREEN);
            questiontrue++;
            score+=5;
            txtscoreDK.setText("Score: "+score);
            edtAnswerDK.clearFocus();

        }
        else{
            Toast.makeText(this, "Sai rồi", Toast.LENGTH_SHORT).show();
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
    public void Anhxa(){
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