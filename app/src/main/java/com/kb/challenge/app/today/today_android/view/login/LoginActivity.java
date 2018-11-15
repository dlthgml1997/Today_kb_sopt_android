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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.login.LoginData;
import com.kb.challenge.app.today.today_android.model.login.LoginResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingActivity;

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
    private TextView login_text_go_to_signup;

    @Override
    public void init() {
        login_edit_id = (EditText)findViewById(R.id.login_edit_id);
        login_edit_passwd = (EditText)findViewById(R.id.login_edit_passwd);
        login_text_go_to_signup =(TextView) findViewById(R.id.login_text_go_to_signup);

        login_button_SignIn = (Button) findViewById(R.id.login_button_SignIn);
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK){
                //회원가입하고 돌아옴. id, passwd 자동 입력
                login_edit_id.setText(data.getStringExtra("id"));
                login_edit_passwd.setText(data.getStringExtra("passwd"));
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        if (SharedPreference.Companion.getInstance().getPrefStringData("data").isEmpty()) {
            Log.v("토큰 없음 ->login 이동","토큰 없음" );

        } else {
            Log.v("토큰 존재", SharedPreference.Companion.getInstance().getPrefStringData("data"));
            Log.v("shinee _ user id ", SharedPreference.Companion.getInstance().getPrefStringData("user_id"));

            if (SharedPreference.Companion.getInstance().getPrefStringData(SharedPreference.Companion.getInstance().getPrefStringData("user_id") + "" + "user_name").isEmpty()) {
                Log.v("이름 없음 ->세팅 이동", SharedPreference.Companion.getInstance().getPrefStringData("user_id"));
                startActivity(new Intent(this, WelcomeActivity.class));

            } else {
                Log.v("이름 존재 ->감정기록 이동", SharedPreference.Companion.getInstance().getPrefStringData(SharedPreference.Companion.getInstance().getPrefStringData("user_id") + "" + "user_name"));
                startActivity(new Intent(this, MainActivity.class));
            }

            finish();

        }

        login_button_SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        login_text_go_to_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        login_text_go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, SignUpActivity.class), 1);
            }
        });

    }

    public void signIn() {
        Log.v("login process", "login process!!!");
        final LoginData loginData = new LoginData(login_edit_id.getText().toString(), login_edit_passwd.getText().toString());
        Call<LoginResponse> requestDetail = networkService.login(loginData);
        requestDetail.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("login process2", "login process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    if (response.body().getMessage().equals("wrong password")) { //패스워드 에러
                        LinearLayout ll_act_login_password_error = (LinearLayout) findViewById(R.id.ll_act_login_password_error);
                        ll_act_login_password_error.setVisibility(View.VISIBLE);
                    } else if (response.body().getMessage().equals("wrong id")) { //아이디 에러
                        LinearLayout ll_act_login_id_error = (LinearLayout) findViewById(R.id.ll_act_login_id_error);
                        ll_act_login_id_error.setVisibility(View.VISIBLE);
                        LinearLayout ll_act_login_password_error = (LinearLayout) findViewById(R.id.ll_act_login_password_error);
                        ll_act_login_password_error.setVisibility(View.VISIBLE);
                    } else {
                        // 로그인 성공
                        LoginResponse loginResponse = response.body();

                        Log.v("token", loginResponse.getToken());
                        SharedPreference.Companion.getInstance().setPrefData("data", loginResponse.getToken());
                        SharedPreference.Companion.getInstance().setPrefData("user_id", loginData.getId());
                        if (SharedPreference.Companion.getInstance().getPrefStringData(SharedPreference.Companion.getInstance().getPrefStringData("user_id")+""+"user_name").isEmpty()) {
                            Log.v("이름 없음 ->세팅 이동",SharedPreference.Companion.getInstance().getPrefStringData("user_id"));
                            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));

                        } else {
                            Log.v("이름 존재 ->감정기록 이동", SharedPreference.Companion.getInstance().getPrefStringData(SharedPreference.Companion.getInstance().getPrefStringData("user_id")+""+"user_name"));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                } else {

                    LinearLayout ll_act_login_id_error = (LinearLayout) findViewById(R.id.ll_act_login_id_error);
                    ll_act_login_id_error.setVisibility(View.VISIBLE);

                    LinearLayout ll_act_login_password_error = (LinearLayout) findViewById(R.id.ll_act_login_password_error);
                    ll_act_login_password_error.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
