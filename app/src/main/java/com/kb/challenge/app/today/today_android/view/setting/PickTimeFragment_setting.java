package com.kb.challenge.app.today.today_android.view.setting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.login.FirstSettingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickTimeFragment_setting extends Fragment implements TimePicker.OnTimeChangedListener {
    private PickTimeFragment_setting.OnFragmentInteractionListener mListener;

    public static PickTimeFragment_setting newInstance(/*String param1, String param2*/) {
        PickTimeFragment_setting fragment = new PickTimeFragment_setting();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_picktime_setting, container, false);
       TimePicker datePicker1 = (TimePicker)view.findViewById(R.id.datePicker1);
       long now = System.currentTimeMillis();
       Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String getTime = sdf.format(date);
        String[] split_time = getTime.split(":");

       datePicker1.setOnTimeChangedListener(this);
       ((FirstSettingActivity)getActivity()).changeBtnAct();
       return view;
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Log.v("timepicker", hourOfDay + " ");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PickTimeFragment_setting.OnFragmentInteractionListener) {
            mListener = (PickTimeFragment_setting.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
