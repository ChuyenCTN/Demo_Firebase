package com.chuyenctn.demo_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText edEmail;
    private EditText edPass;
    private TextView tvSignUp;
    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            // profile Activity here
            finish();
            startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
        }
        edEmail = (EditText) findViewById(R.id.ed_email);
        edPass = (EditText) findViewById(R.id.ed_pass);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_in);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open login activity here
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            }
        });
    }


    public void Register_user(View view) {
        String email = edEmail.getText().toString().trim();
        String password = edPass.getText().toString().trim();

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
        progressBar.setMessage("Registering User...");
        progressBar.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                // profile Activity here
                                finish();
                                startActivity(new Intent(getApplicationContext(),Profile_Activity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Registered Failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.dismiss();
                    }
                });
    }
}
