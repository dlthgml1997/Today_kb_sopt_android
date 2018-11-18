package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.login.LoginData;
import com.kb.challenge.app.today.today_android.model.login.SignupData;
import com.kb.challenge.app.today.today_android.model.login.LoginResponse;
import com.kb.challenge.app.today.today_android.model.login.SignupCheckData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements Init {
    private Button signup_button_SignUp;
    private TextView signup_text_go_to_login;
    private NetworkService networkService;
    private EditText signup_edit_id;
    private EditText signup_edit_passwd;
    private EditText signup_edit_passwd_again;
    private SignupData signupData;
    private BaseModel msg;
    private LinearLayout ll_act_signup_id_error;
    private LinearLayout ll_act_signup_password_error;
    private LoginData loginData;

    @Override
    public void init() {
        signup_edit_id = (EditText) findViewById(R.id.signup_edit_id);
        signup_edit_passwd = (EditText) findViewById(R.id.signup_edit_passwd);
        signup_edit_passwd_again = (EditText) findViewById(R.id.signup_edit_passwd_again);
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();

        ll_act_signup_id_error = (LinearLayout)findViewById(R.id.ll_act_signup_id_error);
        ll_act_signup_password_error = (LinearLayout)findViewById(R.id.ll_act_signup_password_error);

        signup_button_SignUp = (Button) findViewById(R.id.signup_button_SignUp);

        signup_button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("id & passwd", signup_edit_id.getText().toString() + "&" + signup_edit_passwd.getText().toString());
                signupData = new SignupData(signup_edit_id.getText().toString(), signup_edit_passwd.getText().toString());
                if (!signup_edit_passwd.getText().toString().equals(signup_edit_passwd_again.getText().toString())){
                    ll_act_signup_password_error.setVisibility(View.VISIBLE);
                }
                else {
                    ll_act_signup_password_error.setVisibility(View.INVISIBLE);
                    signupCheckID();
                }
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

    public void signupCheckID() {
        Log.v("check process", "check process!!!");

        SignupCheckData data = new SignupCheckData(signupData.getId());
        Call<BaseModel> requestDetail = networkService.signupCheckId(data);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("check process2", "check process2!!!");
                    Log.v("check message", response.body().getMessage().toString());
                    ll_act_signup_id_error.setVisibility(View.INVISIBLE);
                    if (response.body().getMessage().toString().equals("success"))
                        signup();
                }
                else {
                    ll_act_signup_id_error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void signIn() {
        Log.v("login process", "login process!!!");
        String fcm_token = FirebaseInstanceId.getInstance().getToken();

        final LoginData loginData = new LoginData(signupData.getId(),signupData.getPasswd(), fcm_token);
        Call<LoginResponse> requestDetail = networkService.login(loginData);
        requestDetail.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("login process2", "login process2!!!");
                    Log.v("회원가입 페이지 message", response.body().getMessage().toString());

                    // 로그인 성공
                    LoginResponse loginResponse = response.body();

                    Log.v("token", loginResponse.getToken());
                    SharedPreference.Companion.getInstance().setPrefData("data", loginResponse.getToken());
                    startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void signup() {
        Log.v("signup process", "signup process!!!");
        Log.v("signup and usersetting", signupData.toString());

        Call<BaseModel> requestDetail = networkService.signup(signupData);

        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("signup process2", "signup process2!!!");
                    Log.v("message", response.body().getMessage().toString());
                    signIn();
                }

            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
