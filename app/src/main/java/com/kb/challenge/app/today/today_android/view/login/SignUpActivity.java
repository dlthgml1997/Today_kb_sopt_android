package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;

public class SignUpActivity extends AppCompatActivity {
    private Button signup_button_SignUp;
    private TextView signup_text_go_to_login;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
/*
    if(이미존재하는아이디) {
        LinearLayout ll_act_signup_id_error = (LinearLayout) findViewById(R.id.ll_act_signup_id_error);
        ll_act_signup_id_error.setVisibility(View.VISIBLE);

        LinearLayout ll_act_signup_password_error = (LinearLayout) findViewById(R.id.ll_act_signup_password_error);
        ll_act_signup_password_error.setVisibility(View.INVISIBLE);
    }else if(이미존재하는아이디&&패스워드가 일치하지않을때){

            LinearLayout ll_act_signup_id_error = (LinearLayout) findViewById(R.id.ll_act_signup_id_error);
            ll_act_signup_id_error.setVisibility(View.VISIBLE);

            LinearLayout ll_act_signup_password_error = (LinearLayout) findViewById(R.id.ll_act_signup_password_error);
            ll_act_signup_password_error.setVisibility(View.VISIBLE);
        }else if(패스워드가 일치하지않을 때){

            LinearLayout ll_act_signup_id_error = (LinearLayout) findViewById(R.id.ll_act_signup_id_error);
            ll_act_signup_id_error.setVisibility(View.INVISIBLE);

            LinearLayout ll_act_signup_password_error = (LinearLayout) findViewById(R.id.ll_act_signup_password_error);
            ll_act_signup_password_error.setVisibility(View.VISIBLE);
        }else {
        //로그인 성공
        signup_button_SignUp = (Button) findViewById(R.id.signup_button_SignUp);
        signup_button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        }
*/

        signup_button_SignUp = (Button) findViewById(R.id.signup_button_SignUp);
        signup_button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup_text_go_to_login = (TextView) findViewById(R.id.signup_text_go_to_login);
        signup_text_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
}
}
