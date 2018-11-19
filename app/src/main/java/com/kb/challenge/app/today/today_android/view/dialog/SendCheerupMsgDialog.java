package com.kb.challenge.app.today.today_android.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

/**
 * Created by shineeseo on 2018. 11. 17..
 */

public class SendCheerupMsgDialog extends DialogFragment implements Init {
    private NetworkService networkService;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    public static SendCheerupMsgDialog newInstance(String title) {
        SendCheerupMsgDialog frag = new SendCheerupMsgDialog();
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_cheerup_msg, new LinearLayout(getActivity()), false);

        init();

        TextView txt_dialog_cheerup_name = (TextView)view.findViewById(R.id.txt_dialog_cheerup_name);


        // Build dialog
        final Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        return builder;

    }


    }
