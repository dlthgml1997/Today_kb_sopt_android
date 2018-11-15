package com.kb.challenge.app.today.today_android.view.record;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 11..
 */

public class RecordFeelingActivity extends AppCompatActivity implements
        MainBadFragment.OnFragmentInteractionListener,
        MainGoodFragment.OnFragmentInteractionListener {
    String[] feelingMsg = {"건드리면 물어요", "날 내버려둬요", "그저 그래요", "별 생각 없어요", "기분이 좋아요!", "기분이 좋아요!", "오늘 기분 최고!"};
    int[] emotionImg = {R.drawable.img_emotion_bad_3,R.drawable.img_emotion_bad_2,R.drawable.img_emotion_bad_1,R.drawable.img_emotion_soso_0,R.drawable.img_emotion_good_1,R.drawable.img_emotion_good_2,R.drawable.img_emotion_good_3};
    int[] emotionTextImg = {R.drawable.img_text_bad_3,R.drawable.img_text_bad_2,R.drawable.img_text_bad_1,R.drawable.img_text_soso_0,R.drawable.img_text_good_1,R.drawable.img_text_good_2,R.drawable.img_text_good_3};
    private ImageView img_emotion;
    private ImageView img_text;
    private SeekBar seekBar;
    private Button record_save_btn;
    private static int progress_status = 3;
    private NetworkService networkService;
    private ImageView hint_bad_3,hint_bad_2,hint_bad_1,hint_soso_1,hint_good_1,hint_good_2,hint_good_3;
    //    private final static String TOKEN_DATA = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozLCJpYXQiOjE1NDE4NjcxMjEsImV4cCI6MTU0NDQ1OTEyMX0.irjngtenOTszNG3qiJ1XoQB8gRo0RRSA3jPUMbUvqSw";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_feeling);
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        record_save_btn = (Button) findViewById(R.id.record_save_btn);
        img_emotion = (ImageView) findViewById(R.id.img_emotion);
        img_text= (ImageView) findViewById(R.id.img_text);
        //힌트 바꾸기위해 힌트아이디 찾음
        hint_bad_3=(ImageView) findViewById(R.id.hint_bad_3);
        hint_bad_2=(ImageView) findViewById(R.id.hint_bad_2);
        hint_bad_1=(ImageView) findViewById(R.id.hint_bad_1);
        hint_soso_1=(ImageView) findViewById(R.id.hint_soso_1);
        hint_good_1=(ImageView) findViewById(R.id.hint_good_1);
        hint_good_2=(ImageView) findViewById(R.id.hint_good_2);
        hint_good_3=(ImageView) findViewById(R.id.hint_good_3);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        record_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFeeling();
            }
        });

    }

    public void setEmotionImage(int progress_status){
        switch (progress_status) {
            case 0:
                img_emotion.setImageResource(emotionImg[0]);
                img_text.setImageResource(emotionTextImg[0]);

                hint_bad_3.setVisibility(View.VISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                img_emotion.setImageResource(emotionImg[1]);
                img_text.setImageResource(emotionTextImg[1]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.VISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img_emotion.setImageResource(emotionImg[2]);
                img_text.setImageResource(emotionTextImg[2]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.VISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img_emotion.setImageResource(emotionImg[3]);
                img_text.setImageResource(emotionTextImg[3]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.VISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                img_emotion.setImageResource(emotionImg[4]);
                img_text.setImageResource(emotionTextImg[4]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.VISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 5:
                img_emotion.setImageResource(emotionImg[5]);
                img_text.setImageResource(emotionTextImg[5]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.VISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 6:
                img_emotion.setImageResource(emotionImg[6]);
                img_text.setImageResource(emotionTextImg[6]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void recordFeeling() {
        Log.v("feeling save process", "feeling save process!!!");
        FeelingData feelingData = new FeelingData(0, null, feelingMsg[3]);
        switch (progress_status) {
            case 0:
            case 1:
            case 2:
                new FeelingData(null, seekBar.getMax() - progress_status - 3, feelingMsg[0]);
                SharedPreference.Companion.getInstance().setPrefData("feeling_score", seekBar.getMax() - progress_status - 3);
            case 3:
            case 4:
            case 5:
            case 6:
                new FeelingData(seekBar.getMax() - 3, null, feelingMsg[4]);
                SharedPreference.Companion.getInstance().setPrefData("feeling_score", seekBar.getMax() - 3);

        }
        //감정기록하기 (token 값, feelingdata)
        Call<BaseModel> requestDetail = networkService.recordFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), feelingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("feeling save process", "feeling save process!!!");
                    Log.v("message", response.body().getMessage().toString());

                    Log.v("feeling record", progress_status  + " ");
                    if (progress_status < 3) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                        transaction.add(R.id.root_frame, new MainBadFragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                        transaction.commit();

                    } else {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                        transaction.add(R.id.root_frame, new MainGoodFragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                        transaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            if (seekBar.getId() == R.id.seekBar) {
                progress_status = progress;
                Log.v("123", Integer.toString(progress));
                setEmotionImage(progress);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
