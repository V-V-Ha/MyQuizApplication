package com.example.prm392myquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.DatabaseAccess;
import com.example.prm392myquizapplication.other.MessageObject;
import com.example.prm392myquizapplication.other.MyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class OnlineLoginActivity extends AppCompatActivity {

    Button btnDangnhap;
    TextView tvDangky, tvforgotPassword;
    EditText edttaikhoan, edtmatkhau;
    DatabaseAccess DB;
    FirebaseDatabase rootNode; //f_instanse
    DatabaseReference userref; //f_db
    private FirebaseAuth mAuth;
    private final MessageObject messageObject = MessageObject.getInstance();

    public static OnlineLoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_login);

        instance = this;


        AnhXa();
        DB = DatabaseAccess.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //Đăng nhập thành công chuyển sang MainActivity
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edttaikhoan.getText().toString().trim();
                String matkhau = edtmatkhau.getText().toString().trim();


                // validations for input email and password // check th trong
                if (TextUtils.isEmpty(email)) {
                    messageObject.ShowDialogMessage(Gravity.CENTER,
                            OnlineLoginActivity.this,
                            "Please enter your email!!",
                            0);
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    messageObject.ShowDialogMessage(Gravity.CENTER,
                            OnlineLoginActivity.this,
                            "Please enter your password!!",
                            0);
                    return;
                }

                // login existing user

                mAuth.signInWithEmailAndPassword(email, matkhau)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                            "Login Successfully",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            //notify
                                            Intent intent;
                                            intent = new Intent(OnlineLoginActivity.this, MyService.class);
                                            startService(intent);

                                            DB.iduser = mAuth.getCurrentUser().getUid();
                                            DB.CapNhatUser(DB.iduser);
                                            rootNode = FirebaseDatabase.getInstance();
                                            userref = rootNode.getReference("User").child(DB.iduser);
                                            userref.child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                                                    int roleValue = Integer.parseInt(task.getResult().getValue().toString());

//                                                    Intent intent;
//                                                    if (roleValue == 0) {
//                                                        intent = new Intent(OnlineLoginActivity.this, AdminActivity.class);
//                                                    }
//                                                    else {
//                                                        intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                    }
//
//                                                    startActivity(intent);

                                                }
                                            });
                                        }
                                        else {
                                            // sign-in failed
                                            messageObject.ShowDialogMessage(Gravity.CENTER,
                                                    OnlineLoginActivity.this,
                                                    "Wrong information!!",
                                                    0);
                                        }
                                    }
                                });


            }
        });
        tvDangky.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineLoginActivity.this, OnlineSignUpActivity.class);
                startActivity(intent);
            }
        });

        tvforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa()
    {
        btnDangnhap=(Button) findViewById(R.id.btn_login);
        tvDangky = (TextView) findViewById(R.id.textView_register);
        tvforgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
        edttaikhoan = (EditText) findViewById(R.id.edt_username);
        edtmatkhau = (EditText) findViewById(R.id.edt_password);

    }

    public void clickStopService() {
        Intent intent=new Intent(this, MyService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}