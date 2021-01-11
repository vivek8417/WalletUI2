package com.example.walletui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout reg_name, reg_username, reg_email, reg_phone_number,reg_password;
    Button btn_register, btn_backLogin;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        initViews();
        btn_backLogin.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    private void initViews() {
        reg_name=findViewById(R.id.name);
        reg_username=findViewById(R.id.username);
        reg_email=findViewById(R.id.email);
        reg_phone_number=findViewById(R.id.phone);
        reg_password=findViewById(R.id.password);

        btn_backLogin=findViewById(R.id.btn_backtoLogin);
        btn_register=findViewById(R.id.btn_register);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                    rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("users");
                    if(!validateName() | !validateUserName() | !validateEmail() | !validatePassword() | !validatePhoneNo())
                    {
                        return;
                    }

                    //get all values
                    String name=reg_name.getEditText().getText().toString();
                    String username=reg_username.getEditText().getText().toString();
                    String email=reg_email.getEditText().getText().toString();
                    String phoneNo=reg_phone_number.getEditText().getText().toString();
                    String password=reg_password.getEditText().getText().toString();

                    UserHelperClass helperClass=new UserHelperClass(name,username,email,phoneNo,password);

                    reference.child(username).setValue(helperClass);
                    break;

            case R.id.btn_backtoLogin:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

        }

    }

    private Boolean validateName()
    {
        String val =reg_name.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_name.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUserName()
    {
        String val =reg_username.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{4,10}\\z";

        if(val.isEmpty())
        {
            reg_username.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=10){
            reg_username.setError("User name is to long");
            return false;
        }else if (!val.matches(noWhiteSpace))
        {
            reg_username.setError("White space are not allowed");
            return false;
        }
        else {
            reg_username.setError(null);
            reg_username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail()
    {
        String val =reg_email.getEditText().getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            reg_email.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern))
        {
            reg_email.setError("Invalid Email address!");
            return false;
        }
        else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo()
    {
        String val =reg_phone_number.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_phone_number.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_phone_number.setError(null);
            return true;
        }
    }
    private Boolean validatePassword()
    {
        String val =reg_password.getEditText().getText().toString();
        if(val.isEmpty())
        {
            reg_password.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_password.setError(null);
            return true;
        }
    }
}