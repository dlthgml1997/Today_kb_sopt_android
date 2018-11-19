package com.kb.challenge.app.today.today_android.view.community;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kb.challenge.app.today.today_android.R;

public class CheerupDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dialog_cheerup_msg, container, false);


        // remove dialog title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // remove dialog background
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        return view;
    }
}
