package com.blogspot.okyser.learnearn;

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

public class RegisterActivity extends AppCompatActivity {
    EditText uname,upassword,usemail;
    Button regbtn;
    TextView Alrlgn;
    String add;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname=findViewById(R.id.username);
        usemail=findViewById(R.id.useremail);
        upassword=findViewById(R.id.userpassword);
        regbtn=findViewById(R.id.btnregister);
        Alrlgn=findViewById(R.id.alreadylogin);
        firebaseAuth=FirebaseAuth.getInstance();
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {


                    String user_email=usemail.getText().toString().trim();
                    String user_pass=upassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });
        Alrlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean validate()
    {
        boolean result=false;
        String name=uname.getText().toString();
        String email=usemail.getText().toString();
        String password=upassword.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }
        return result;

    }
}
