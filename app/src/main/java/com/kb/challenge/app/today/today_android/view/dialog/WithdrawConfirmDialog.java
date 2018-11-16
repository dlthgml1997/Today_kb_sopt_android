package com.kb.challenge.app.today.today_android.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 17..
 */

public class WithdrawConfirmDialog extends DialogFragment implements Init {

    private NetworkService networkService;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    public static WithdrawConfirmDialog newInstance(String title) {
        WithdrawConfirmDialog frag = new WithdrawConfirmDialog();
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_qna, new LinearLayout(getActivity()), false);

        init();

        TextView btn_cancel_txt = (TextView) view.findViewById(R.id.btn_cancel_txt);

        TextView btn_withdraw_confirm = (TextView)view.findViewById(R.id.btn_withdraw_confirm);


        // Build dialog
        final Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        btn_cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btn_withdraw_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSavingList();
                getActivity().finish();
            }
        });
        return builder;
    }

    public void deleteSavingList() {
        Log.v("deleteSavingList", "deleteSavingList process!!!");
        Call<BaseModel> requestDetail = networkService.deleteDeposit(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("deleteSavingList", "deleteSavingList process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
