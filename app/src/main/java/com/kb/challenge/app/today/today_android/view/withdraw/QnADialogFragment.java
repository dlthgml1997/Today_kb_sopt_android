package com.kb.challenge.app.today.today_android.view.withdraw;


import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;

public class QnADialogFragment extends DialogFragment {
    private EditText edit_cheerup_msg;
    private TextView txt_dialog_cheerup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_qna, container, false);

        edit_cheerup_msg = (EditText) view.findViewById(R.id.edit_cheerup_msg);
        txt_dialog_cheerup = (TextView) view.findViewById(R.id.txt_dialog_cheerup);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // remove dialog background
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ChangeMsgByEmotion();
        return view;
    }


    public void ChangeMsgByEmotion() {

        //기분이 좋으면
        txt_dialog_cheerup.setText(Html.fromHtml("승언님은 오늘 <B>기분이 좋아요!</B>"));
        edit_cheerup_msg.setHint("친구에게 응원의 메시지 한 마디 어때요?");
        //기분이 안좋으면
        txt_dialog_cheerup.setText(Html.fromHtml("승언님은 오늘 <B>기분이 안좋아요</B>"));
        edit_cheerup_msg.setHint("친구에게 위로의 메시지 한 마디 어때요?");
    }
}