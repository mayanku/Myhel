package com.tut.mayank.myhel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage,textbook;
    private FirebaseAuth mAuth;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent self=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(self);

                    return true;
                case R.id.navigation_dashboard:
                    Intent mapa=new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(mapa);
                    return true;
                case R.id.navigation_notifications:
                    FirebaseAuth.getInstance().signOut();
                    Intent start=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(start);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        textbook=(TextView)findViewById(R.id.textbook);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        textbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book=new Intent(getApplicationContext(),BookActivity.class);
                startActivity(book);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mAuth.getCurrentUser();
        if(currentuser==null){
            Intent move=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(move);
            finish();
        }

    }

}
