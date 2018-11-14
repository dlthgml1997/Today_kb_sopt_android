package com.kb.challenge.app.today.today_android.view.login;

import java.text.DecimalFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CustomTextWatcher implements TextWatcher {
    @SuppressWarnings("unused")
    private EditText mEditText;
    private LinearLayout ll_settitle_over_amount_error;
    String strAmount = ""; // 임시저장값 (콤마)
    public CustomTextWatcher(EditText e, LinearLayout l) {
        mEditText = e;
        ll_settitle_over_amount_error= l;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

      /*  if (!s.toString().equals(strAmount)) { // StackOverflow를 막기위해,
            strAmount = makeStringComma(s.toString().replace(",", ""));
            mEditText.setText(strAmount);
            Editable e = mEditText.getText();
            Selection.setSelection(e, strAmount.length());
            }*/ //이거 하면 3,000 되는 순간 에러! (int가 아니라서)
        if(s.toString().length() > 0){
            if(Integer.parseInt(s.toString()) > 300000){
                mEditText.setText(null);
                ll_settitle_over_amount_error.setVisibility(View.VISIBLE);
            }else{
                ll_settitle_over_amount_error.setVisibility(View.INVISIBLE);
            }
        }
        }



    protected String makeStringComma(String str) {
        if (str.length() == 0)
            return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }

}
