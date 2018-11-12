package com.kb.challenge.app.today.today_android.view.coin;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.coin.CoinDetailData;
import com.kb.challenge.app.today.today_android.model.coin.CoinDetailResponse;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingItem;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.coin.adapter.CoinSavingListAdapter;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class CoinFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NetworkService networkService;
    private RecyclerView mRecyclerView;
    private ArrayList<CoinSavingItem> coinSavingItems;
    private TextView coin_cur_money;

    private CoinFragment.OnFragmentInteractionListener mListener;

    public CoinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoinFragment newInstance(/*String param1, String param2*/) {
        CoinFragment fragment = new CoinFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 200){

        }

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

        View view = inflater.inflate(R.layout.fragment_coin, container, false);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        TextView coin_name_txt = (TextView) view.findViewById(R.id.coin_name_txt);
        TextView coin_target_money_txt = (TextView)view.findViewById(R.id.coin_target_money_txt);
        coin_cur_money = (TextView)view.findViewById(R.id.coin_cur_money);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.coin_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (SharedPreference.Companion.getInstance().getPrefIntegerData("totalMoney")< 0) {
            Log.v("total empty", "total empty");

        }

        if (SharedPreference.Companion.getInstance().getPrefStringData("goal").isEmpty() || SharedPreference.Companion.getInstance().getPrefIntegerData("goalMoney")<0) {
            Log.v("goal empty", "goal empty");
            getSavingDetail();
        }
        else {
            coin_name_txt.setText(SharedPreference.Companion.getInstance().getPrefStringData("goal"));
            coin_target_money_txt.setText(String.valueOf(SharedPreference.Companion.getInstance().getPrefIntegerData("goalMoney")));
        }
        coin_name_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getSavingList();

        Button coin_btn_withdrawal = (Button)view.findViewById(R.id.coin_btn_withdrawal);
        coin_btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSavingList();
                getSavingList();
            }
        });


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
        if (context instanceof CoinFragment.OnFragmentInteractionListener) {
            mListener = (CoinFragment.OnFragmentInteractionListener) context;
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
    public void getSavingList() {
        Log.v("savingList process", "savingList process!!!");
        Call<CoinSavingResponse> requestDetail = networkService.getSavingList(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<CoinSavingResponse>() {
            @Override
            public void onResponse(Call<CoinSavingResponse> call, Response<CoinSavingResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("savingList process2", "savingList process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    int totalMoney = response.body().getTotalMoney();
                    coinSavingItems = response.body().getData();

                    Log.v("totalMoney", totalMoney + " ");
                    SharedPreference.Companion.getInstance().setPrefData("totalMoney", totalMoney);
                    coin_cur_money.setText(String.valueOf(totalMoney));

                    CoinSavingListAdapter coinSavingListAdapter = new CoinSavingListAdapter(getActivity(),coinSavingItems);

                    mRecyclerView.setAdapter(coinSavingListAdapter);

                }
            }

            @Override
            public void onFailure(Call<CoinSavingResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void getSavingDetail() {
        Log.v("getSavingDetail process", "getSavingDetail process!!!");
        Call<CoinDetailResponse> requestDetail = networkService.getSavingDetail(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<CoinDetailResponse>() {
            @Override
            public void onResponse(Call<CoinDetailResponse> call, Response<CoinDetailResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getSavingDetail", "getSavingDetail process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    ArrayList<CoinDetailData> coinDetailData = response.body().getData();
                    Log.v("coin detail", coinDetailData.toString());

//                    if (coinDetailData != null) {
//                        SharedPreference.Companion.getInstance().setPrefData("goal", coinDetailData.get(0).getGoal());
//                        SharedPreference.Companion.getInstance().setPrefData("goalMoney", coinDetailData.get(0).getGoal_money());
//
//                    }
                }
            }

            @Override
            public void onFailure(Call<CoinDetailResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void deleteSavingList() {
        Log.v("deleteSavingList", "deleteSavingList process!!!");
        Call<BaseModel> requestDetail = networkService.deleteDeposit(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("deleteSavingList", "deleteSavingList process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
