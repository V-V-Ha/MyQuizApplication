package com.example.prm392myquizapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.other.MessageObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText EmailF;
    private Button btnResetP;

    FirebaseAuth mAuth;
    private final MessageObject messageObject = MessageObject.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EmailF = findViewById(R.id.etEmailForgot);
        btnResetP = findViewById(R.id.btnResetPass);

        mAuth = FirebaseAuth.getInstance();

        btnResetP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = EmailF.getText().toString().trim();

        if(email.isEmpty()){
            EmailF.setError("Please enter your Email!");
            EmailF.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailF.setError("Please enter correct Email!");
            EmailF.requestFocus();
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Please check your email to get new password!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),OnlineLoginActivity.class);
                    startActivity(intent);
                }
                else {
                    messageObject.ShowDialogMessage(Gravity.CENTER,
                            ForgotPasswordActivity.this,
                            "NOT SUCCESSFUL! Please check again!",
                            0);
                }

            }
        });
    }
}