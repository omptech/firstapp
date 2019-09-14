package com.blogspot.okyser.learnearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView textView,attempt,forgotpassword;
    EditText email,password;
    int counter=5;
    Button login;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=findViewById(R.id.tvsignup);
        attempt=findViewById(R.id.tvattempt);
        email=findViewById(R.id.etname);
        password=findViewById(R.id.etpassword);
        login=findViewById(R.id.button);
        forgotpassword=findViewById(R.id.tvforgotpass);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        attempt.setText("No of attempts remaining:");
        if(user!=null)
        {
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String uemail=email.getText().toString();
               String upass= password.getText().toString();
               if(uemail.isEmpty() || upass.isEmpty())
               {
                   Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
               }
                else {
                   validate(uemail, upass);
               }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,PasswordActivity.class));
            }
        });
    }

    private void validate(String useremail,String userpassword)
    {

        progressDialog.setMessage("Please wait until you are verified");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful())
              {
                  progressDialog.dismiss();
                 // Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                 // startActivity(new Intent(LoginActivity.this,MainActivity.class));
                  EmailVerfication();
              }
              else
              {
                  Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                  counter--;
                  attempt.setText("No of attempts remaining"+counter);
                  progressDialog.dismiss();
                  if(counter==0)
                  {
                      login.setEnabled(false);
                  }
              }
            }
        });
    }

    private void EmailVerfication()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag)
        {
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        else
        {
            Toast.makeText(this, "Verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}

