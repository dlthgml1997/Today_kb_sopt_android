package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

import java.io.InputStream;

public class FirstSettingActivity extends AppCompatActivity {
    int position = 0;

    private ImageView iv_setname_user_image;

    Long backKeyPressedTime = 0L;

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

        View header = getLayoutInflater().inflate(R.layout.fragment_setname, null, false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container1, new DotFirstFragment());
        fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment());
        fragmentTransaction.commit();

        iv_setname_user_image= header.findViewById(R.id.iv_setname_user_image);
        iv_setname_user_image.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                Log.i("TedPark","눌렸다");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

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
                    position++;


                } else {

                    // 이 때 서버로 초기설정 정보를 넘겨준다.
                    Intent intent = new Intent(FirstSettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    iv_setname_user_image.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //초기설정에서 뒤로가기 버튼 누르면 이전으로 가게끔
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (position == 0) {
            Intent intent = new Intent(FirstSettingActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container1, new DotSecondFragment());
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frag_container2, new SetNameFragment());
            fragmentTransaction.commit();

            position--;
        } else if (position == 2) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container1, new DotThirdFragment());
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frag_container2, new PickTimeFragment());
            fragmentTransaction.commit();

            position--;
        } else if (position == 3) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frag_container2, new SetTitleFragment());
            fragmentTransaction.commit();

            position--;


        } else if (position == 4) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_container1, new DotForthFragment());
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frag_container2, new AccountConnectFragment());
            fragmentTransaction.commit();

            position--;
        } else {
            this.finish();
        }


    }



}
