package com.tut.mayank.myhel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    EditText editadd,editname,editmobile;
    DatabaseReference mdatabase;
    Button payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        editadd=(EditText)findViewById(R.id.editadd);
        editmobile=(EditText)findViewById(R.id.editmobile);
        editname=(EditText)findViewById(R.id.editname);
        payment=(Button)findViewById(R.id.payment);
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentuser.getUid();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
      payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editname.getText().toString();
                String mobile=editmobile.getText().toString();
                String address=editadd.getText().toString();
                HashMap<String,Object> details=new HashMap<>();
                details.put("name",name);
                details.put("mobile",mobile);
                details.put("address to be delivered",address);

                mdatabase.updateChildren(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                          Intent forward=new Intent(getApplicationContext(),PaymentActivity.class);
                           startActivity(forward);
                        }
                        else {
                            Intent back=new Intent(getApplicationContext(),DetailsActivity.class);
                            startActivity(back);
                        }
                    }

                });



            }
        });

    }
    }

