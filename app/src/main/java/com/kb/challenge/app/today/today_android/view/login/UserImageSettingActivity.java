package com.kb.challenge.app.today.today_android.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;

public class UserImageSettingActivity extends AppCompatActivity {
    private ImageView iv_setname_user_image;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    iv_setname_user_image= findViewById(R.id.iv_setname_user_image);
    iv_setname_user_image.setOnClickListener(new View.OnClickListener() { //error @.setOnClickListener Cannot resolve symbol

        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"눌렸어",Toast.LENGTH_SHORT).show();

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Profile Image"), 1);*/
        }
    });
    }
}
