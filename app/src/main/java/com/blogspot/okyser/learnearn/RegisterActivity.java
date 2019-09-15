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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText uname,upassword,usemail,userage;
    Button regbtn;
    TextView Alrlgn;
    String add;
    FirebaseAuth firebaseAuth;
    String name,email,password,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname=findViewById(R.id.username);
        usemail=findViewById(R.id.useremail);
        upassword=findViewById(R.id.userpassword);
        regbtn=findViewById(R.id.btnregister);
        Alrlgn=findViewById(R.id.alreadylogin);
        userage=findViewById(R.id.etage);
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
                                sendemailverification();
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
         name=uname.getText().toString();
         email=usemail.getText().toString();
         password=upassword.getText().toString();
         age=userage.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || userage.equals(""))
        {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }
        return result;

    }
    private void sendemailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      senduserdata();
                      Toast.makeText(RegisterActivity.this, "Successfully Registered,Verification mail sent!", Toast.LENGTH_SHORT).show();
                      firebaseAuth.signOut();
                      finish();
                      startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                  }
                  else
                  {
                      Toast.makeText(RegisterActivity.this, "Verfiction mail has'nt been sent", Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
    }
    private void senduserdata()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile=new UserProfile(age,email,name);
        myref.setValue(userProfile);
    }
}
