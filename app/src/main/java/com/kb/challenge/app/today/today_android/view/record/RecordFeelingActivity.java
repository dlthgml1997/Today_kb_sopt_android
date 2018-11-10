package com.kb.challenge.app.today.today_android.view.record;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 11..
 */

public class RecordFeelingActivity extends AppCompatActivity implements MainBadFragment.OnFragmentInteractionListener, MainGoodFragment.OnFragmentInteractionListener {
    String[] feelingMsg = {"건드리면 물어요", "날 내버려둬요", "그저 그래요", "별 생각 없어요", "기분이 좋아요!", "기분이 좋아요!", "오늘 기분 최고!"};
    private TextView record_feeling_variation_txt;
    private SeekBar seekBar;
    private Button record_save_btn;
    private static int progress_status = 0;
    private NetworkService networkService;

    //    private final static String TOKEN_DATA = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozLCJpYXQiOjE1NDE4NjcxMjEsImV4cCI6MTU0NDQ1OTEyMX0.irjngtenOTszNG3qiJ1XoQB8gRo0RRSA3jPUMbUvqSw";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_feeling);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        record_save_btn = (Button) findViewById(R.id.record_save_btn);
        record_feeling_variation_txt = (TextView) findViewById(R.id.record_feeling_variation_txt);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        record_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFeeling();
            }
        });

    }

    public void recordFeeling() {
        Log.v("feeling save process", "feeling save process!!!");

        FeelingData feelingData = new FeelingData(0, null, record_feeling_variation_txt.getText().toString());
        switch (progress_status) {
            case 0:
            case 1:
            case 2:
                new FeelingData(null, seekBar.getMax() - progress_status - 3, record_feeling_variation_txt.getText().toString());
            case 3:
            case 4:
            case 5:
            case 6:
                new FeelingData(seekBar.getMax() - 3, null, record_feeling_variation_txt.getText().toString());
        }

        //감정기록하기 (token 값, feelingdata)
        Call<BaseModel> requestDetail = networkService.recordFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), feelingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("feeling save process", "feeling save process!!!");
                    Log.v("message", response.body().getMessage().toString());

                    Intent intent = new Intent();

                    Log.v("feeling record", progress_status  + " ");
                    if (progress_status < 3) {
                        intent.putExtra("feeling_record", progress_status);
                        SharedPreference.Companion.getInstance().setPrefData("feeling_score", seekBar.getMax() - progress_status - 3);

                    } else {
                        intent.putExtra("feeling_record", progress_status);
                        SharedPreference.Companion.getInstance().setPrefData("feeling_score", seekBar.getMax() - 3);

                    }
                    setResult(200);
                    finish();


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

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            if (seekBar.getId() == R.id.seekBar) {
                progress_status = progress;
                record_feeling_variation_txt.setText(feelingMsg[progress]);
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
