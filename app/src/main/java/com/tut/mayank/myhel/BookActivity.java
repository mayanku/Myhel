package com.tut.mayank.myhel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookActivity extends AppCompatActivity {
    DatabaseReference mdatabase;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        button=(Button)findViewById(R.id.button);
        radioButton1=(RadioButton)findViewById(R.id.radioButton1);
        radioButton2=(RadioButton)findViewById(R.id.radioButton2);
        radioButton3=(RadioButton)findViewById(R.id.radioButton3);
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentuser.getUid();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
      final   HashMap<String,Object> user=new HashMap<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButton1.isChecked()){
                    user.put("type","grocery");
                    mdatabase.updateChildren(user);
                    Intent move=new Intent(getApplicationContext(),DetailsActivity.class);
                    startActivity(move);
                }
                else if (radioButton2.isChecked()){
                    user.put("type","document");
                    mdatabase.updateChildren(user);
                   // Intent move=new Intent(getApplicationContext(),DetailsActivity.class);
                    //startActivity(move);
                }
                else{}
                  //  user.put("type","other work");
                //mdatabase.updateChildren(user);
            }
        });}

    }

