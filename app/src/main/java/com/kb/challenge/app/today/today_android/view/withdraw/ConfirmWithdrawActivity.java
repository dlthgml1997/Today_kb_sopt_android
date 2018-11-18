package com.kb.challenge.app.today.today_android.view.withdraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.dialog.WithdrawConfirmDialog;
import com.kb.challenge.app.today.today_android.view.login.DotFifthFragment;
import com.kb.challenge.app.today.today_android.view.login.DotFirstFragment;
import com.kb.challenge.app.today.today_android.view.login.DotForthFragment;
import com.kb.challenge.app.today.today_android.view.login.DotSecondFragment;
import com.kb.challenge.app.today.today_android.view.login.DotThirdFragment;


public class ConfirmWithdrawActivity extends AppCompatActivity {//implements ConfilmFragment.OnFragmentInteractionListener

    int position = 0;
    Long backKeyPressedTime = 0L;
    private Button btn_withdraw_yes;
    private Button btn_withdraw_no;
    private char trim_string = '\"';
    private TextView txt_confirm_withdraw;
    private Bundle bundle;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corfilmwithdraw);

        btn_withdraw_no = (Button) findViewById(R.id.btn_withdraw_no);
        btn_withdraw_yes = (Button) findViewById(R.id.btn_withdraw_yes);
        txt_confirm_withdraw = (TextView) findViewById(R.id.txt_confirm_withdraw);

        fragment = new ConfilmFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        bundle = new Bundle(2);
        Intent intent = getIntent();
        String goal = intent.getStringExtra("goal");
        String goal_money = intent.getStringExtra("goal_money");

        Log.v("goal&goal_money", goal + " " + goal_money);
        bundle.putString("goal", goal);
        bundle.putString("goal_money", goal_money);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, fragment, "Comfirm1");
        fragmentTransaction.commit();
        txt_confirm_withdraw.setText(Html.fromHtml("정말 <B>인출</B>하실건가요?"));


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
                    fragment = new ConfilmFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotSecondFragment());
                    fragmentTransaction.replace(R.id.frag_container2, fragment);
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText(Html.fromHtml("<B>목표</B>가 있지 않았나요?"));

                    position++;
                } else if (position == 1) {
                    fragment = new ConfilmFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotThirdFragment());
                    fragmentTransaction.replace(R.id.frag_container2, fragment);
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText(Html.fromHtml("<B>후회</B>하게 될지도 몰라요"));
                    position++;
                } else if (position == 2) {
                    fragment = new ConfilmFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
                    fragmentTransaction.replace(R.id.frag_container2, fragment);
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText(Html.fromHtml("<B>중요한 일</B>이 있으신거죠?"));

                    position++;
                } else if (position == 3) {
                    fragment = new ConfilmFragment();
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotFifthFragment());
                    fragmentTransaction.replace(R.id.frag_container2, fragment);
                    fragmentTransaction.commit();

                    txt_confirm_withdraw.setText(Html.fromHtml("분명 <B>꼭 필요한 곳</B>이죠?"));

                    btn_withdraw_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            WithdrawConfirmDialog dialog = new WithdrawConfirmDialog();
                            dialog.show(getFragmentManager(), "example");
                        }
                    });
                    position++;
                }
            }
        });

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
