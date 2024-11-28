package com.credoapp.parent.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.FeesHistoryAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.databinding.ActivityFeeHistoryBinding;
import com.credoapp.parent.model.FeeHistoryResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;

import org.parceler.Parcels;

public class FeeHistoryScreen extends ParentActivity {
    private static final String TAG = FeeHistoryScreen.class.getSimpleName();
    ActivityFeeHistoryBinding feeHistoryBinding;
    private StudentInfo studentInfo;
    String adminId,accdomicId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_fee_history);
        feeHistoryBinding = (ActivityFeeHistoryBinding) viewDataBinding;
        setupActionBar("Fee History");
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        requestForPayment();

    }



    private void requestForPayment() {
        Utils.showProgressBar(this);
        StudentPresenter studentPresenter = new StudentPresenter(this);
        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());
        jsonObject.addProperty("student_id", studentInfo.getStudent_id());
        jsonObject.addProperty("academic_year",accdomicId);
        studentPresenter.onRequestFeeHistory(jsonObject);
    }

    public void onSuccessFeeHistory(FeeHistoryResponse feeHistoryResponse) {
        Log.d(TAG, "onSuccessFeeHistory " + feeHistoryResponse);
        Utils.hideProgressBar();
        if (feeHistoryResponse.getCode() == 200) {
            feeHistoryBinding.feeHistoryList.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FeeHistoryScreen.this, LinearLayoutManager.VERTICAL, false);
            feeHistoryBinding.feeHistoryList.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(feeHistoryBinding.feeHistoryList.getContext(),
                    linearLayoutManager.getOrientation());
            FeesHistoryAdapter parentStudentsAdapter = new FeesHistoryAdapter(this, feeHistoryResponse.getPaymentHistories());
            feeHistoryBinding.feeHistoryList.addItemDecoration(dividerItemDecoration);
            feeHistoryBinding.feeHistoryList.setAdapter(parentStudentsAdapter);
        } else {
            feeHistoryBinding.feeHistoryList.setVisibility(View.GONE);
            Toast.makeText(this, feeHistoryResponse.getDescription(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onFailureFeeHistory(String message) {
        Utils.hideProgressBar();
        Log.d(TAG, "onSuccessFeeHistory " + message);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        Utils.hideProgressBar();
      requestForPayment();
    }

}
