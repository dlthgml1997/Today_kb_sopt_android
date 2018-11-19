package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class FirstActivity extends AppCompatActivity {
    private Button btn_login;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
