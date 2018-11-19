package com.kb.challenge.app.today.today_android.view.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 7..
 */

public class MainDepositFragment extends Fragment implements Init {
    private TextView txt_good_deposit_total_money;
    private TextView txt_good_deposit_user_name;
    private TextView txt_average_coin_mini;
    private TextView txt_average_coin;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "MainDepositFragment";

    private MainDepositFragment.OnFragmentInteractionListener mListener;

    private NetworkService networkService;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    public MainDepositFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDepositFragment newInstance(/*String param1, String param2*/) {
        MainDepositFragment fragment = new MainDepositFragment();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.main_record_good_deposit, container, false);

        txt_good_deposit_user_name= (TextView)view.findViewById(R.id.txt_good_deposit_user_name );

        txt_good_deposit_total_money= (TextView)view.findViewById(R.id.txt_good_deposit_total_money );

        Bundle bundle = getArguments();
        String user_name = bundle.getString("user_name");
        int total_money = bundle.getInt("total_money");

        Log.v("user_name, total", user_name + " " + total_money);

        txt_good_deposit_user_name.setText(user_name);
        txt_good_deposit_total_money.setText(total_money + "");
        return view;

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainDepositFragment.OnFragmentInteractionListener) {
            mListener = (MainDepositFragment.OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    public void getSavingList() {
//        Log.v("savingList process", "savingList process!!!");
//        Call<CoinSavingResponse> requestDetail = networkService.getSavingList(SharedPreference.Companion.getInstance().getPrefStringData("data"));
//        requestDetail.enqueue(new Callback<CoinSavingResponse>() {
//            @Override
//            public void onResponse(Call<CoinSavingResponse> call, Response<CoinSavingResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.v("savingList process2", "savingList process2!!!");
//                    Log.v("saving list get message", response.body().getMessage().toString());
//                    Log.v("coin saving list res", response.body().toString());
//
//                    total_money = response.body().getTotalMoney();
//                    Log.v("total toatl", total_money + "");
//                    txt_good_deposit_total_money.setText(String.valueOf(total_money));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CoinSavingResponse> call, Throwable t) {
//                Log.i("err saving", t.getMessage());
//            }
//        });
//    }
}
