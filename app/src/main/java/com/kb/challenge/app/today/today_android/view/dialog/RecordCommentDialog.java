package com.kb.challenge.app.today.today_android.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;

/**
 * Created by shineeseo on 2018. 11. 16..
 */

public class RecordCommentDialog extends DialogFragment {
    public static RecordCommentDialog newInstance(String title) {
        RecordCommentDialog frag = new RecordCommentDialog();
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

//        final Dialog go_to_record_feeling_dialog = new Dialog(getActivity());
//        go_to_record_feeling_dialog.setContentView(R.layout.dialog_record_feeling_main);
//        go_to_record_feeling_dialog.setTitle("감정 기록 하러 가기");
//
//        btn_ok_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.root_frame2, new RecordFeelingFragment());
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.addToBackStack(null);
//
//                transaction.commit();
//            }
//        });
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_record_comment, new LinearLayout(getActivity()), false);

        // Retrieve layout elements
        TextView btn_ok_dialog = (TextView) view.findViewById(R.id.btn_ok_dialog);
        btn_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new RecordFeelingFragment();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.root_frame2, fragment);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.addToBackStack(null);
//
//                transaction.commit();

            }
        });

        TextView btn_cancel_dialog = (TextView)view.findViewById(R.id.btn_cancel_dialog);

        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;

    }

}
