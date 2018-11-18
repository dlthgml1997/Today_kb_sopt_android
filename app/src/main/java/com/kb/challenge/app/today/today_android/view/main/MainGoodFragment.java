package com.kb.challenge.app.today.today_android.view.main;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingData;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 7..
 */

public class MainGoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "MainGoodFragment";

    private Spinner deposit_spinner;
    private NetworkService networkService;
    private MainGoodFragment.OnFragmentInteractionListener mListener;
    private int totalMoney;
    private String user_name;
    private String comment;
    private int total_money;

    public MainGoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainGoodFragment newInstance(/*String param1, String param2*/) {
        MainGoodFragment fragment = new MainGoodFragment();
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

        View view = inflater.inflate(R.layout.main_record_good, container, false);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        TextView main_name_txt = (TextView) view.findViewById(R.id.main_name_txt);

        deposit_spinner = (Spinner) view.findViewById(R.id.deposit_spinner);
        //스피너 어댑터 설정

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.deposit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deposit_spinner.setAdapter(adapter);

        int good = getArguments().getInt("feeling_data");
        user_name = getArguments().getString("user_name");
        main_name_txt.setText(user_name + "님!");

        Log.v("good", good + "");
        deposit_spinner.setSelection(good);

        //스피너 이벤트 발생
        deposit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView btn_main_deposit = (TextView) view.findViewById(R.id.btn_main_deposit);

        btn_main_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog send_deposit_comment_dialog = new Dialog(getActivity());
                send_deposit_comment_dialog.setContentView(R.layout.dialog_record_comment);
                send_deposit_comment_dialog.setTitle("comment Dialog");

                TextView deposit_dialog_name = (TextView)send_deposit_comment_dialog.findViewById(R.id.deposit_dialog_name);

                deposit_dialog_name.setText(user_name + "님,");

                final EditText deposit_edit_comment = (EditText)send_deposit_comment_dialog.findViewById(R.id.deposit_edit_comment);


                TextView btn_cancel_dialog = (TextView)send_deposit_comment_dialog.findViewById(R.id.btn_cancel_dialog);

                TextView btn_ok_dialog = (TextView)send_deposit_comment_dialog.findViewById(R.id.btn_ok_dialog);

                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_deposit_comment_dialog.dismiss();
                    }
                });

                btn_ok_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        comment = deposit_edit_comment.getText().toString();
                        Log.v("deposit comment", comment);
                        getSavingList();
                        savingMoney();
                        send_deposit_comment_dialog.dismiss();
                    }
                });
                send_deposit_comment_dialog.show();

            }
        });

        TextView btn_do_not_deposit = (TextView)view.findViewById(R.id.btn_do_not_deposit);
        btn_do_not_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MainDepositFragment();
                Bundle bundle = new Bundle(1);
                bundle.putString("user_name", user_name);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                transaction.replace(R.id.root_frame, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();

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
        if (context instanceof MainGoodFragment.OnFragmentInteractionListener) {
            mListener = (MainGoodFragment.OnFragmentInteractionListener) context;
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

    public void savingMoney() {
        Log.v("saving process", "saving process!!!");
        CoinSavingData coinSavingData = new CoinSavingData(Integer.parseInt(deposit_spinner.getSelectedItem().toString()), comment);
        Call<BaseModel> requestDetail = networkService.savingMoney(SharedPreference.Companion.getInstance().getPrefStringData("data"), coinSavingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("saving process2", "saving process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    Fragment fragment = new MainDepositFragment();
                    Bundle bundle = new Bundle(1);
                    bundle.putString("user_name", user_name);
                    bundle.putInt("total_money", total_money);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                    transaction.replace(R.id.root_frame, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                    transaction.commit();
                }

            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
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

                    total_money = response.body().getTotalMoney();
                    Log.v("total toatl", total_money + "");

                }
            }

            @Override
            public void onFailure(Call<CoinSavingResponse> call, Throwable t) {
                Log.i("err saving", t.getMessage());
            }
        });
    }
}
