package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.setting.UserSettingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.MainActivity;

import java.sql.Time;
import java.text.ParseException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstSettingActivity extends AppCompatActivity implements
        PickTimeFragment.OnTimePickerSetListener,
        SetNameFragment.OnEditNameSetListener,
        SetTitleFragment.OnEditTitleSetListener, Init,
        SetNameFragment.OnProfileImageSetListener,
        SetNameFragment.OnFragmentInteractionListener,
        SetTitleFragment.OnFragmentInteractionListener{
    // private  CustomViewPager mPager;

    int position = 0;
    Long backKeyPressedTime = 0L;
    int h;
    int m;
    String userName;
    String title;
    MultipartBody.Part profile_image;
    int amount;
    private NetworkService networkService;
    private UserSettingData userSettingData;
    private Button btn_act_firstsetting_next_active;
    private Button btn_act_firstsetting_next;


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);
    }

    @Override
    public void onProfileImageSet(MultipartBody.Part image) {
        profile_image = image;
        Log.v("profile_image_comming!", profile_image + "");
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
        Log.v("userName_comming!", userName + "");

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
        btn_act_firstsetting_next_active = (Button) findViewById(R.id.btn_act_firstsetting_next_active);
        btn_act_firstsetting_next = (Button) findViewById(R.id.btn_act_firstsetting_next);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment(), "SetName");
        fragmentTransaction.commit();

        btn_act_firstsetting_next_active.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeBtn();
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

                    btn_act_firstsetting_next_active.setText("완료");
                    btn_act_firstsetting_next_active.setOnClickListener(new View.OnClickListener() {
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
                }
            }
        });

    }


    public void setUserSetting() throws ParseException {
        Log.v("setUserSetting process", "setUserSetting process!!!");

        Time time_data = new Time(h, m, 00);
        Log.v("push time", time_data + " ");


        RequestBody name_body =
                RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody title_body =
                RequestBody.create(MediaType.parse("text/plain"), title);


        userSettingData = new UserSettingData(userName, title, amount, time_data);

        Log.v("usersetting data", userSettingData.toString());

        Call<BaseModel> requestDetail = networkService.userSetting(SharedPreference.Companion.getInstance().getPrefStringData("data"), name_body, profile_image, title_body, amount, time_data);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("setUserSetting process2", "setUserSetting process2!!!");
                    Log.v("message", response.body().getMessage().toString());
                    Intent intent = new Intent(FirstSettingActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }


            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }


    public void changeBtnAct() {
        btn_act_firstsetting_next_active.setTextColor(Color.parseColor("#424242"));
        btn_act_firstsetting_next_active.setVisibility(View.VISIBLE);
        btn_act_firstsetting_next.setVisibility(View.GONE);
    }

    public void changeBtn() {
        btn_act_firstsetting_next_active.setVisibility(View.GONE);
        btn_act_firstsetting_next.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  //앨범에서 가져온 이미지를 뷰에 표시하는 함수
        // Check which request we're responding to
        if (requestCode == 1111) {
            if (resultCode == RESULT_OK) {
                Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("SetName");
                if (fragment != null) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
        if (requestCode == 1112) {
            if (resultCode == RESULT_OK) {
                Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("SetName");
                if (fragment != null) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }


    //초기설정에서 뒤로가기 버튼 누르면 이전으로 가게끔
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container2);
        Log.v("setting fragment back", fragment.getClass().getName());

        if (getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStack();
        else {
            super.onBackPressed();

        }

    }

}
