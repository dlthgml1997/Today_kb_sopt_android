package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.setting.UserSettingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.InputStream;

public class FirstSettingActivity extends AppCompatActivity implements
        PickTimeFragment.OnTimePickerSetListener,
        SetNameFragment.OnEditNameSetListener,
        SetTitleFragment.OnEditTitleSetListener, Init,
        SetNameFragment.OnProfileImageSetListener{
    // private  CustomViewPager mPager;

    int position = 0;

    private ImageView iv_setname_user_image;

    Long backKeyPressedTime = 0L;
    int h;
    int m;
    String userName;
    String title;
    String profile_image;
    int amount;
    private NetworkService networkService;
    private UserSettingData userSettingData;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);
    }

    @Override
    public void onProfileImageSet(String image) {
        profile_image = image;
    }

    @Override
    public void onAmountSet(int amount) {
        this.amount = amount;
    }

    @Override
    public void onTitleSet(String title) {
        this.title = title;
    }

    @Override
    public void onNameSet(String name) {
        userName = name;
    }

    @Override
    public void onTimePickerSet(int hour, int min) {
        h = hour;
        m = min;
    }

/*
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > (backKeyPressedTime + 2000)) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast toast = Toast.makeText(getApplicationContext(),"\'뒤로 가기\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100);
            toast.show();

            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            this.finish();

        }

    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstsetting);

        init();

        View header = getLayoutInflater().inflate(R.layout.fragment_setname, null, false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment());
        fragmentTransaction.commit();



/*
        ImageView iv_setname_user_image = findViewById(R.id.iv_setname_user_image);
        iv_setname_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstSettingActivity.this, UserImageSettingActivity.class);
                startActivity(intent);

            }
        });
*/
        Button btn_act_first_set_next = (Button) findViewById(R.id.btn_act_first_set_next);
        btn_act_first_set_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position == 0) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotSecondFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new PickTimeFragment());
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    fragmentTransaction.commit();
                    position++;
                } else if (position == 1) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotThirdFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new SetTitleFragment());
                    fragmentTransaction.commit();

                    position++;
                } else if (position == 2) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new AccountConnectFragment());
                    fragmentTransaction.commit();

                    position++;
                } else if (position == 3) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotFifthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new AccountCreateFragment());
                    fragmentTransaction.commit();


                    Button btn_act_first_set_next = (Button) findViewById(R.id.btn_act_first_set_next);
                    btn_act_first_set_next.setText("완료");
                    btn_act_first_set_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                setUserSetting();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    position++;


                } else {

                    // 이 때 서버로 초기설정 정보를 넘겨준다.
                    Intent intent = new Intent(FirstSettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void setUserSetting() throws ParseException {
        Log.v("setUserSetting process", "setUserSetting process!!!");

        Time time_data = new Time(h, m, 00);
        Log.v("push time", time_data + " ");


        userSettingData = new UserSettingData(userName, title, amount,time_data);
        Log.v("usersetting data", userSettingData.toString());
        Call<BaseModel> requestDetail = networkService.userSetting(SharedPreference.Companion.getInstance().getPrefStringData("data"), userSettingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("setUserSetting process2", "setUserSetting process2!!!");
                    Log.v("message", response.body().getMessage().toString());
                    SharedPreference.Companion.getInstance().setPrefData("goal_amount", userSettingData.getGoal_money());
                    SharedPreference.Companion.getInstance().setPrefData("goal_title", userSettingData.getGoal());
//                    SharedPreference.Companion.getInstance().setPrefData("push_time", userSettingData.getPush_time());
                    SharedPreference.Companion.getInstance().setPrefData("user_name", userSettingData.getName());

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }


    //초기설정에서 뒤로가기 버튼 누르면 이전으로 가게끔
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (position == 0) {
//            Intent intent = new Intent(FirstSettingActivity.this, WelcomeActivity.class);
//            startActivity(intent);
//        } else if (position == 1) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frag_container1, new DotSecondFragment());
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//            fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment());
//            fragmentTransaction.commit();
//
//            position--;
//        } else if (position == 2) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frag_container1, new DotThirdFragment());
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//            fragmentTransaction.replace(R.id.frag_container2, new PickTimeFragment());
//            fragmentTransaction.commit();
//
//            position--;
//        } else if (position == 3) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//            fragmentTransaction.replace(R.id.frag_container2, new SetTitleFragment());
//            fragmentTransaction.commit();
//
//            position--;
//
//
//        } else if (position == 4) {
//
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//            fragmentTransaction.replace(R.id.frag_container2, new AccountConnectFragment());
//            fragmentTransaction.commit();
//
//            position--;
//        } else {
//            this.finish();
//        }
//

    }

}
