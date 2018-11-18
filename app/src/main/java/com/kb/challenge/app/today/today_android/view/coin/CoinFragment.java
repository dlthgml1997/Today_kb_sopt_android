package com.kb.challenge.app.today.today_android.view.coin;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.kb.challenge.app.today.today_android.view.withdraw.ConfirmWithdrawActivity;

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
    private TextView coin_name_txt;
    private TextView coin_target_money_txt;
    private RelativeLayout rl_coin_goal_box;
    private ImageView img_coin_pig;
    private Button coin_btn_withdrawal;
    private ImageView ic_qna_coin;

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

        if (resultCode == 200) {

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

        Window window1 = getActivity().getWindow();
        int whitecolor = getResources().getColor(R.color.colorBackground);
        window1.setStatusBarColor(whitecolor);
        View view = inflater.inflate(R.layout.fragment_coin, container, false);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        coin_name_txt = (TextView) view.findViewById(R.id.coin_name_txt);
        coin_target_money_txt = (TextView) view.findViewById(R.id.coin_target_money_txt);
        coin_cur_money = (TextView) view.findViewById(R.id.coin_cur_money);
        rl_coin_goal_box = (RelativeLayout) view.findViewById(R.id.rl_coin_goal_box);
        img_coin_pig = (ImageView) view.findViewById(R.id.img_coin_pig);
        coin_btn_withdrawal = (Button) view.findViewById(R.id.coin_btn_withdrawal);
        ic_qna_coin = (ImageView) view.findViewById(R.id.ic_qna_coin);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.coin_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getSavingDetail();
        //changeGoalBackground();
        getSavingList();

        Button coin_btn_withdrawal = (Button) view.findViewById(R.id.coin_btn_withdrawal);
        coin_btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog with_draw_confirm_dialog = new Dialog(getActivity());
                with_draw_confirm_dialog.setContentView(R.layout.dialog_withdraw);

                with_draw_confirm_dialog.setTitle("withdraw Dialog");

                TextView btn_cancel_dialog = (TextView) with_draw_confirm_dialog.findViewById(R.id.btn_cancel_dialog);

                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        with_draw_confirm_dialog.dismiss();
                    }
                });

                TextView btn_withdraw_dialog = (TextView) with_draw_confirm_dialog.findViewById(R.id.btn_withdraw_dialog);

                btn_withdraw_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        with_draw_confirm_dialog.dismiss();
                        Intent intent = new Intent(getActivity(), ConfirmWithdrawActivity.class);
                        intent.putExtra("goal", coin_name_txt.getText().toString());
                        intent.putExtra("goal_money", coin_target_money_txt.getText().toString());
                        startActivity(intent);
                    }
                });
                with_draw_confirm_dialog.show();
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


    public void setStatusbarColor() { //평소 스태터스바

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
    public void onResume() {
        super.onResume();
        getSavingList();
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
                    Log.v("saving list get message", response.body().getMessage().toString());
                    Log.v("coin saving list res", response.body().toString());

                    int totalMoney = response.body().getTotalMoney();
                    coinSavingItems = response.body().getData();
                    Log.v("coin saving list", coinSavingItems.toString());
                    Log.v("totalMoney", totalMoney + " ");
                    coin_cur_money.setText(String.valueOf(totalMoney));
//목표 달성시
                    if (Integer.parseInt(coin_cur_money.getText().toString()) >= Integer.parseInt(coin_target_money_txt.getText().toString())) {
                        rl_coin_goal_box.setBackgroundResource(R.drawable.img_coin_pig_success);
                        img_coin_pig.setVisibility(View.INVISIBLE);
                        coin_btn_withdrawal.setText("목표달성! 인출하기");
                        ic_qna_coin.setImageResource(R.drawable.ic_coin_coin_white_16_px);
                        coin_btn_withdrawal.setBackgroundResource(R.drawable.button_border3);
                        Window window = getActivity().getWindow();
//                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUND);
                        int color = getResources().getColor(R.color.dandelion);
                        window.setStatusBarColor(color);

                        coin_btn_withdrawal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {  // 목적 달성시 뜨는 다이얼로그
                                final Dialog dialog_congratulation = new Dialog(getActivity());
                                dialog_congratulation.setContentView(R.layout.dialog_congratulation);

                                dialog_congratulation.setTitle("congratulation");

                                TextView btn_cancel_dialog_con = (TextView)dialog_congratulation.findViewById(R.id.btn_cancel_dialog);

                                btn_cancel_dialog_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog_congratulation.dismiss();
                                    }
                                });

                                TextView btn_withdraw_dialog_con = (TextView)dialog_congratulation.findViewById(R.id.btn_withdraw_dialog);

                                btn_withdraw_dialog_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog_congratulation.dismiss();
                                        deleteSavingList();
                                        onResume();

                                    }
                                });

                                dialog_congratulation.show();

                            }
                        });

                    }
                    CoinSavingListAdapter coinSavingListAdapter = new CoinSavingListAdapter(getActivity(), coinSavingItems);

                    mRecyclerView.setAdapter(coinSavingListAdapter);
                }
            }

            @Override
            public void onFailure(Call<CoinSavingResponse> call, Throwable t) {
                Log.i("err saving", t.getMessage());
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
                    coin_name_txt.setText(coinDetailData.get(0).getGoal());
                    coin_target_money_txt.setText(String.valueOf(coinDetailData.get(0).getGoal_money()));

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
