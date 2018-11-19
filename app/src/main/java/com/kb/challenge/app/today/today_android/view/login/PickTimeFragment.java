package com.kb.challenge.app.today.today_android.view.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.kb.challenge.app.today.today_android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickTimeFragment extends Fragment implements TimePicker.OnTimeChangedListener {
    private OnTimePickerSetListener onTimePickerSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_picktime, container, false);
       TimePicker datePicker1 = (TimePicker)view.findViewById(R.id.datePicker1);
       long now = System.currentTimeMillis();
       Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String getTime = sdf.format(date);
        String[] split_time = getTime.split(":");

       onTimePickerSetListener.onTimePickerSet(Integer.parseInt(split_time[0]), Integer.parseInt(split_time[1]));
       datePicker1.setOnTimeChangedListener(this);
       ((FirstSettingActivity)getActivity()).changeBtnAct();
       return view;
    }
    public interface OnTimePickerSetListener {
        void onTimePickerSet(int hour, int min);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onTimePickerSetListener.onTimePickerSet(hourOfDay, minute);
        Log.v("timepicker", hourOfDay + " ");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimePickerSetListener) {
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimePickerSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onTimePickerSetListener = null;
    }
}
