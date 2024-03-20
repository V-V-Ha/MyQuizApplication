package com.example.prm392myquizapplication;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.dao.DatabaseAccess;
import com.example.prm392myquizapplication.data.VemoQuiz;
import com.example.prm392myquizapplication.data.OnlineUser;

import java.util.ArrayList;



public class QuizActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    DatabaseAccess DB;
    TextView txtscore,txtquestcount,txtquestion,txttime;
    RadioGroup rdgchoices;
    RadioButton btnop1,btnop2,btnop3,btnop4;
    Button btnconfirm;
    Button btnquit;
    private ArrayList<VemoQuiz> vemoQuizs;
    int questioncurrent = 0;
    int questiontrue = 0;
    int answer=0;
    int score=0;
    int idbo;

    OnlineUser user;

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

        vemoQuizs = new ArrayList<>();
        AddArrayCTN();

        if(vemoQuizs.size() <= 0) {
            Toast.makeText(QuizActivity.this, "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!", Toast.LENGTH_LONG).show();
            Intent error = new Intent(QuizActivity.this, VemoQuizActivity.class);
            finish();
            startActivity(error);
        }
        else {
            shownextquestion(questioncurrent, vemoQuizs);

            CountDownTimer countDownTimer=new CountDownTimer(3000,300) {
                @Override
                public void onTick(long millisUntilFinished) {
                    showanswer();
                }

                @Override
                public void onFinish() {

                    btnconfirm.setEnabled(true);
                    shownextquestion(questioncurrent, vemoQuizs);
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
                            VemoQuizActivity.class);
                    startActivity(intent);
                }
            });
        }

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
        vemoQuizs.clear();

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

            vemoQuizs.add(new VemoQuiz(idcau,idbo,noidung,A,B,C,D,True));
        }
    }

    public void LayUser()
    {
        database = Database.initDatabase(QuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        String Email = cursor.getString(2);
        String SDT = cursor.getString(3);
;
        user = new OnlineUser(Iduser,HoTen,Email,SDT);
    }

    public void shownextquestion(int pos, ArrayList<VemoQuiz> vemoQuizs){

        txtquestcount.setText("Question: "+(questioncurrent+1)+"/"+ vemoQuizs.size());
        rdgchoices.clearCheck();
        btnop1.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop2.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop3.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        btnop4.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        if(pos== vemoQuizs.size()){

            Intent intent=new Intent(QuizActivity.this,FinishQuizActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("questiontrue", questiontrue);
            intent.putExtra("qcount", pos);
            startActivity(intent);
        }
        else {
            answer=Integer.valueOf(vemoQuizs.get(pos).getDapanTrue());
            txtquestion.setText(vemoQuizs.get(pos).getNoidung());
            btnop1.setText(vemoQuizs.get(pos).getDapanA());
            btnop2.setText(vemoQuizs.get(pos).getDapanB());
            btnop3.setText(vemoQuizs.get(pos).getDapanC());
            btnop4.setText(vemoQuizs.get(pos).getDapanD());
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

        txtscore.setText("Score: "+score);
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