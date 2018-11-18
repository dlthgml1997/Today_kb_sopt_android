package com.kb.challenge.app.today.today_android.view.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kb.challenge.app.today.today_android.R;

public class AccountCreateFragment extends Fragment {
    private RelativeLayout rl_account_create;
    private ImageView account_create_view;
    private ImageView pig;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account2, container, false);

        account_create_view = v.findViewById(R.id.account_create_view);
        pig= v.findViewById(R.id.account_create_view);
        pig.bringToFront();
        account_create_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pig.setImageResource(R.drawable.img_createaccount_active);

                pig.bringToFront();
                ((FirstSettingActivity)getActivity()).changeBtnAct();
            }
        });
        return v;
    }
}