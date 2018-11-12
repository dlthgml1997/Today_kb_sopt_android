package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

public class FirstSettingActivity extends AppCompatActivity {
    // private  CustomViewPager mPager;
    int position = 0;
    Long backKeyPressedTime = 0L;

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

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstsetting);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment());
        fragmentTransaction.commit();

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
                } else if(position == 2){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new AccountConnectFragment());
                    fragmentTransaction.commit();

                    position++;
                } else if(position == 3){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container1, new DotFifthFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.frag_container2, new AccountCreateFragment());
                    fragmentTransaction.commit();


                    Button btn_act_first_set_next = (Button) findViewById(R.id.btn_act_first_set_next);
                    btn_act_first_set_next.setText("완료");
                    position++;
                } else{
                    Intent intent = new Intent(FirstSettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
