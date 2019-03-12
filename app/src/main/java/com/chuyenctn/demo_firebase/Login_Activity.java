package com.chuyenctn.demo_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private TextView tvUserLogin;
    private EditText edEmailLogin;
    private EditText edPassLogin;
    private TextView tvSignUp;
    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser()!=null){
            // profile Activity here
            finish();
            startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
        }
        tvUserLogin = (TextView) findViewById(R.id.tv_user_Login);
        edEmailLogin = (EditText) findViewById(R.id.ed_email_Login);
        edPassLogin = (EditText) findViewById(R.id.ed_pass_Login);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void Login_user(View view) {
        String email = edEmailLogin.getText().toString().trim();
        String password = edPassLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            // to stopping the function
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            // to stopping the function
            return;
        }
        progressBar.setMessage("Login Please wait...");
        progressBar.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   progressBar.dismiss();
                   if (task.isSuccessful()){
                       finish();
                       startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
                   }
                    }
                });


    }
}
