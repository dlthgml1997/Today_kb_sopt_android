package com.kb.challenge.app.today.today_android.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

public class SetNameFragment extends Fragment {
    private OnEditNameSetListener onEditNameSetListener;
    private EditText et_setname_user_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setname, container, false);
        et_setname_user_name = (EditText) view.findViewById(R.id.et_setname_user_name);
        et_setname_user_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                onEditNameSetListener.onNameSet(et_setname_user_name.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });

        return view;
    }


    public interface OnEditNameSetListener {
        void onNameSet(String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SetNameFragment.OnEditNameSetListener) {
            onEditNameSetListener = (SetNameFragment.OnEditNameSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onEditNameSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onEditNameSetListener = null;
    }
}