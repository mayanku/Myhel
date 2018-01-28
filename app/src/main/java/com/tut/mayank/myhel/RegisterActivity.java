package com.tut.mayank.myhel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
  EditText editname,editemail,editpass;
    Button button3;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editname=(EditText)findViewById(R.id.editname);
        editemail=(EditText)findViewById(R.id.editemail);
        editpass=(EditText)findViewById(R.id.editpass);
        button3=(Button)findViewById(R.id.button3);
        mAuth=FirebaseAuth.getInstance();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editemail.getText().toString();
                String pass=editpass.getText().toString();
                reg_user(email,pass);
            }
        });
    }

    private void reg_user(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 Intent loga=new Intent(getApplicationContext(),LoginActivity.class);
                 startActivity(loga);
             }
                else
             {
                 Toast.makeText(getApplicationContext(),"err",Toast.LENGTH_LONG).show();
             }
            }
        });

    }
}
