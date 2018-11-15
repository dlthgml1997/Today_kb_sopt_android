package com.kb.challenge.app.today.today_android.view.withdraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.setting.UserSettingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.coin.CoinFragment;
import com.kb.challenge.app.today.today_android.view.login.AccountConnectFragment;
import com.kb.challenge.app.today.today_android.view.login.AccountCreateFragment;
import com.kb.challenge.app.today.today_android.view.login.DotFifthFragment;
import com.kb.challenge.app.today.today_android.view.login.DotFirstFragment;
import com.kb.challenge.app.today.today_android.view.login.DotForthFragment;
import com.kb.challenge.app.today.today_android.view.login.DotSecondFragment;
import com.kb.challenge.app.today.today_android.view.login.DotThirdFragment;
import com.kb.challenge.app.today.today_android.view.login.LoginActivity;
import com.kb.challenge.app.today.today_android.view.login.PickTimeFragment;
import com.kb.challenge.app.today.today_android.view.login.SetNameFragment;
import com.kb.challenge.app.today.today_android.view.login.SetTitleFragment;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

import java.sql.Time;
import java.text.ParseException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmWithdrawActivity extends AppCompatActivity {//implements ConfilmFragment.OnFragmentInteractionListener

    int position = 0;
    Long backKeyPressedTime = 0L;
    private Button btn_withdraw_yes;
    private Button btn_withdraw_no;
    private char trim_string = '\"';
    private TextView txt_confirm_withdraw;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corfilmwithdraw);

//        final Handler mHandler= new Handler();
//        final Message msg = mHandler.obtainMessage();
//        final Bundle bundle = new Bundle();

        btn_withdraw_no = (Button) findViewById(R.id.btn_withdraw_no);
        btn_withdraw_yes = (Button) findViewById(R.id.btn_withdraw_yes);
        view = getLayoutInflater().inflate(R.layout.fragment_confirm, null, false);
        txt_confirm_withdraw = (TextView) view.findViewById(R.id.txt_confirm_withdraw);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, new ConfilmFragment(), "Comfirm1");
        fragmentTransaction.commit();

        btn_withdraw_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0)
                    getFragmentManager().popBackStack();
                else
                    onBackPressed();
            }
        });

        btn_withdraw_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Fragment fragment = new ConfilmFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                    bundle.putString("changeText", "목표가 있지 않았나요?"); // key , value
                    fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frag_container1, new DotSecondFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, fragment);
                    fragmentTransaction.commit();


                    position++;
                } else if (position == 1) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotThirdFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new ConfilmFragment());
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText("후회하게 될지도 몰라요");
                    position++;
                } else if (position == 2) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new ConfilmFragment());
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText("중요한 일이 있으신거죠?");
//                    bundle.putInt("position", position);
//                    msg.setData(bundle);
//                    mHandler.sendMessage(msg);
                    position++;
                } else if (position == 3) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotFifthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new ConfilmFragment());
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText("분명 꼭 필요한 곳이죠?");

                    btn_withdraw_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                                CoinFragment.deleteSavingList();
//                            CoinFragment.getSavingList();
//                            CoinFragment.changeGoalBackground();
                            // replaceFragment(new CoinFragment());
                        }
                    });
                    position++;
                }
            }
        });

    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_frame, fragment);
        fragmentTransaction.commit();
    }

    //초기설정에서 뒤로가기 버튼 누르면 이전으로 가게끔
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }


}
