package com.kb.challenge.app.today.today_android.view.login;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.login.LoginData;
import com.kb.challenge.app.today.today_android.model.login.LoginResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class LoginActivity extends AppCompatActivity implements Init {
    private EditText login_edit_id;
    private EditText login_edit_passwd;
    private Button login_button_SignIn;
    private NetworkService networkService;

    @Override
    public void init() {
        login_edit_id = (EditText)findViewById(R.id.login_edit_id);
        login_edit_passwd = (EditText)findViewById(R.id.login_edit_passwd);

        login_button_SignIn = (Button) findViewById(R.id.login_button_SignIn);
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if (SharedPreference.Companion.getInstance().getPrefStringData("data").isEmpty()) {
            Log.v("토큰 없음 ->login 이동", SharedPreference.Companion.getInstance().getPrefStringData("data"));

        } else {
            Log.v("토큰 존재 ->main이동", SharedPreference.Companion.getInstance().getPrefStringData("data"));

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
        login_button_SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               signIn();
            }
        });

    }
    public void signIn() {
        Log.v("login process", "login process!!!");
        LoginData loginData = new LoginData(login_edit_id.getText().toString(), login_edit_passwd.getText().toString());
        Call<LoginResponse> requestDetail = networkService.login(loginData);
        requestDetail.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("login process2", "login process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    LoginResponse loginResponse = response.body();

                    Log.v("token",loginResponse.getToken());
                    SharedPreference.Companion.getInstance().setPrefData("data", loginResponse.getToken());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
