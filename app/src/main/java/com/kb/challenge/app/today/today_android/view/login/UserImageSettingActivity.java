package com.kb.challenge.app.today.today_android.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kb.challenge.app.today.today_android.R;

import java.io.InputStream;
/*
public class UserImageSettingActivity extends AppCompatActivity {
    private ImageView iv_setname_user_image;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_setname);
            iv_setname_user_image= findViewById(R.id.iv_setname_user_image);
            iv_setname_user_image.setOnClickListener(new View.OnClickListener() { //error @.setOnClickListener Cannot resolve symbol

        public void onClick(View v){
            Log.i("TedPark","눌렸다");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
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
                    Intent intent = new Intent(UserImageSettingActivity.this, FirstSettingActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
*/