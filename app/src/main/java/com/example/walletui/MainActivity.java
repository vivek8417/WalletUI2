package com.example.walletui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUp,logIn;
    ImageView image;
    TextView sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        signUp=findViewById(R.id.sign_up);
        image=findViewById(R.id.logo_image);
        sloganText=findViewById(R.id.slogan_name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        logIn=findViewById(R.id.logIn);

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_up:
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                Pair[] pairs=new Pair[6];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(sloganText,"logo_dsce");
                pairs[2]=new Pair<View,String>(username,"edt_username");
                pairs[3]=new Pair<View,String>(password,"edt_password");
                pairs[4]=new Pair<View,String>(logIn,"btn_logIn");
                pairs[5]=new Pair<View,String>(signUp,"btn_signup");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
                break;

            case R.id.logIn:
                if(!validatePassword() | !validateUserName())
                {
                    return;
                }
                else {
                    isUser();
                }
                break;
        }

    }

    private void isUser() {
        String enteredUserName=username.getEditText().getText().toString().trim();
        String enteredPassword=password.getEditText().getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

        Query checkUser =reference.orderByChild("username").equalTo(enteredUserName);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB=snapshot.child(enteredUserName).child("password").getValue(String.class);

                    if(passwordFromDB.equals(enteredPassword))
                    {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDb=snapshot.child(enteredUserName).child("name").getValue(String.class);
                        String usernameFromDb=snapshot.child(enteredUserName).child("username").getValue(String.class);
                        String emailFromDb=snapshot.child(enteredUserName).child("email").getValue(String.class);
                        String phoneNoFromDb=snapshot.child(enteredUserName).child("phoneNo").getValue(String.class);

                        Intent intent=new Intent(getApplicationContext(),UserProfile.class);
                        intent.putExtra("name",nameFromDb);
                        intent.putExtra("username",usernameFromDb);
                        intent.putExtra("email",emailFromDb);
                        intent.putExtra("phoneNo",phoneNoFromDb);

                        startActivity(intent);

                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private Boolean validateUserName()
    {
        String val =username.getEditText().getText().toString();

        if(val.isEmpty())
        {
            username.setError("Field cannot be empty");
            return false;
        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword()
    {
        String val =password.getEditText().getText().toString();
        if(val.isEmpty())
        {
            password.setError("Field cannot be empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}