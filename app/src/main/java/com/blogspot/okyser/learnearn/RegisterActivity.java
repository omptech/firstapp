package com.blogspot.okyser.learnearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText uname,upassword,usemail;
    Button regbtn;
    TextView Alrlgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        if(name.isEmpty() && email.isEmpty() && password.isEmpty())
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
