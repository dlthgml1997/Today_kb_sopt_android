package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.login.LoginData;
import com.kb.challenge.app.today.today_android.model.login.LoginResponse;
import com.kb.challenge.app.today.today_android.model.login.SignupData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import org.w3c.dom.Text;

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
    private LoginData signupData;
    private BaseModel msg;
    private LinearLayout ll_act_signup_id_error;
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
        signup_button_SignUp = (Button) findViewById(R.id.signup_button_SignUp);
        signup_button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("id & passwd", signup_edit_id.getText().toString() + "&" + signup_edit_passwd.getText().toString());
                signupData = new LoginData(signup_edit_id.getText().toString(), signup_edit_passwd.getText().toString());
                signupCheckID();
//                signup();
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

    public void signup() {
        Log.v("signup process", "signup process!!!");

        Call<BaseModel> requestDetail = networkService.signup(signupData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("signup process2", "signup process2!!!");
                    Log.v("message", response.body().getMessage().toString());
                    if (response.body().getMessage().toString().equals("success")) {
//                        Intent intent = new Intent();
//                        intent.putExtra("id", signupData.getId());
//                        intent.putExtra("passwd", signupData.getPasswd());
//                        setResult(RESULT_OK, intent);
//                        finish();
                        Intent intent = new Intent(SignUpActivity.this, FirstSettingActivity.class);
                        intent.putExtra("id", signupData.getId());
                        intent.putExtra("passwd", signupData.getPasswd());
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void signupCheckID() {
        Log.v("check process", "check process!!!");

        SignupData data = new SignupData(signupData.getId());
        Call<BaseModel> requestDetail = networkService.signupCheckId(data);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("check process2", "check process2!!!");
                    Log.v("message", response.body().getMessage().toString());
                    if (response.body().getMessage().toString().equals("success"))
                        signup();
                    else {
                        ll_act_signup_id_error.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
