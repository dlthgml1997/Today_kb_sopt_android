package com.kb.challenge.app.today.today_android.view.withdraw;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;

public class ConfilmFragment extends Fragment{

    private ConfilmFragment.OnFragmentInteractionListener mListener;

    public ConfilmFragment() {

    }

    public static ConfilmFragment newInstance(/*String param1, String param2*/) {
        ConfilmFragment fragment = new ConfilmFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        TextView txt_confirm_withdraw = (TextView)view.findViewById(R.id.txt_confirm_withdraw);

        Bundle bundle = getArguments();

        TextView coin_name_txt = (TextView)view.findViewById(R.id.coin_name_txt);
        TextView coin_target_money_txt = (TextView)view.findViewById(R.id.coin_target_money_txt);

        Log.v("goal&goal_money", bundle.getString("goal") + " " + bundle.getString("goal_money"));

        coin_name_txt.setText(bundle.getString("goal"));
        coin_target_money_txt.setText(bundle.getString("goal_money"));
       // Bundle bundle = getArguments();

      //  txt_confirm_withdraw.setText(bundle.getString("changeText"));


        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof ConfilmFragment.OnFragmentInteractionListener) {
//            mListener = (ConfilmFragment.OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}