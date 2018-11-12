package com.kb.challenge.app.today.today_android.view.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kb.challenge.app.today.today_android.R;

public class SetTitleFragment extends Fragment {
    private EditText et_set_title_amount;
    private LinearLayout ll_settitle_over_amount_error;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settitle, container, false);

        et_set_title_amount = view.findViewById(R.id.et_set_title_amount);
        ll_settitle_over_amount_error = view.findViewById(R.id.ll_settitle_over_amount_error);
        et_set_title_amount.addTextChangedListener(new CustomTextWatcher(et_set_title_amount,ll_settitle_over_amount_error));


        return view;
    }
}