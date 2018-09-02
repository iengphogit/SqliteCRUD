package com.avaboy.btb.crudsqlite.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avaboy.btb.crudsqlite.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameEdt, userPasswordEdt;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        userNameEdt = findViewById(R.id.user_name);
        userPasswordEdt = findViewById(R.id.user_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                doLogin();
                break;
        }
    }


    private boolean doLogin(){

        String userName = userNameEdt.getText().toString();
        String userPassword = userPasswordEdt.getText().toString();

        if(userName.isEmpty()){
            userNameEdt.setError("Please input Username");
            userNameEdt.requestFocus();
        }else if(userPassword.isEmpty()){
            userPasswordEdt.setError("Please input User password");
            userPasswordEdt.requestFocus();
        }else{
            if(getValidUserNamePassword(new UserLogin(userName,userPassword))){
                //Success
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                //Fail
                Toast.makeText(this,"Incorrect Username and Password",Toast.LENGTH_SHORT).show();
            }


        }

        return false;
    }

    private boolean getValidUserNamePassword(UserLogin user){
        return user.getUserName().equalsIgnoreCase("user1") && user.getUserPassword().equals("1234");
    }


    class UserLogin{
        private String userName,userPassword;

        public UserLogin(){}

        public UserLogin(String userName, String userPassword) {
            this.userName = userName;
            this.userPassword = userPassword;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }
    }

}
