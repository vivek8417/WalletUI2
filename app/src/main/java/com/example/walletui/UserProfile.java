package com.example.walletui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView name, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initViews();

        Intent intent=getIntent();
        String full_name=intent.getStringExtra("name");
        //String user_name=intent.getStringExtra("username");

        name.setText(full_name);
       // username.setText(user_name);
    }

    private void initViews() {
        name=findViewById(R.id.full_name);
        username=findViewById(R.id.username1);
    }
}