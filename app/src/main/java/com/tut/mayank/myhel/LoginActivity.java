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

public class LoginActivity extends AppCompatActivity {
  Button signup,login;
    EditText editemailu,editpassu;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        editemailu=(EditText)findViewById(R.id.editemailu);
        editpassu=(EditText)findViewById(R.id.editpassu);
        signup=(Button)findViewById(R.id.btnreg);
        login=(Button)findViewById(R.id.btnlogin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(reg);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editemailu.getText().toString();
                String pass=editpassu.getText().toString();
                loginuser(email,pass);

            }
        });
    }

    private void loginuser(String email, String pass) {
      mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  Intent mainin=new Intent(getApplicationContext(),MainActivity.class);
                  startActivity(mainin);
              }
              else{
                  Toast.makeText(getApplicationContext(),"Wrong inputs",Toast.LENGTH_LONG).show();
              }
          }
      });
    }
}
