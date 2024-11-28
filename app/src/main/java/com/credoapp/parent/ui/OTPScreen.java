package com.credoapp.parent.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


import com.credoapp.parent.adapter.OTPAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.OTPReqeust;
import com.credoapp.parent.model.otpModels.ResendOtpRequest;
import com.credoapp.parent.presenter.LoginPresenter;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.OtpScreenBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPScreen extends AppCompatActivity {

    private OtpScreenBinding binding;
    private static final String TAG = OTPScreen.class.getSimpleName();
    String[] numberOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "0", "11"};

    int count = 0;
    private String user_mobile;
    boolean isFromResend;
    private boolean new_password;
    StudentPresenter studentPresenter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.otp_screen);
        binding = (OtpScreenBinding) viewDataBinding;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        user_mobile = getIntent().getStringExtra("user_mobile");

        studentPresenter = new StudentPresenter(this);
        //9492553525 8179160056
        isFromResend = getIntent().getBooleanExtra("isFromResend", false);
        new_password = getIntent().getBooleanExtra("new_password", false);

        binding.verificationMessage.setText("Please type the verification code sent \n to +91 " + user_mobile);

        binding.submitText.setOnClickListener(v -> {
            String otpFromPin =binding.pinEnterView.getText().toString();

            if (otpFromPin.equals("")){
                Toast.makeText(OTPScreen.this, "Enter otp", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content),"Enter otp",Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (otpFromPin.length()==4){
                verifyOTP();
            }else {
                Toast.makeText(OTPScreen.this, "Enter four digit otp", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content),"Enter four digit otp",Snackbar.LENGTH_SHORT).show();
            }

        });

        binding.textResend.setOnClickListener(v -> {
            Utils.showProgressBar(OTPScreen.this);
            ResendOtpRequest request = new ResendOtpRequest();
            request.setMobile(user_mobile);
            if (Constants.haveInternet(getApplicationContext())) {
                ITutorSource.getRestAPI().resendOtpRe(request).enqueue(new Callback<GlobalResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                        Log.d(TAG, "onResponse donation list: " + response);
                        otpResponse(Objects.requireNonNull(response.body()));
                        Utils.hideProgressBar();
                    }
                    @Override
                    public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Utils.hideProgressBar();
                    }
                });
            } else {
                Utils.hideProgressBar();
                Constants.InternetSettings(OTPScreen.this);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.numberOpionsList.setLayoutManager(gridLayoutManager);
        binding.numberOpionsList.setAdapter(new OTPAdapter(this, numberOptions));
    }

    private void otpResponse(GlobalResponse body) {
        if (body.getResponseCode().equals("200")){
            Toast.makeText(this, "Otp sent to your mobile no.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, body.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeValue() {
        if (!TextUtils.isEmpty(binding.editTextfour.getText().toString())) {
            binding.editTextfour.setText("");
            count--;
            return;
        }
        if (!TextUtils.isEmpty(binding.editTextthree.getText().toString())) {
            binding.editTextthree.setText("");
            count--;
            return;
        }
        if (!TextUtils.isEmpty(binding.editTextTwo.getText().toString())) {
            binding.editTextTwo.setText("");
            count--;
            return;
        }
        if (!TextUtils.isEmpty(binding.editTextone.getText().toString())) {
            binding.editTextone.setText("");
            count--;
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateValue(String numberOption) {
        if (TextUtils.isEmpty(binding.editTextone.getText().toString())) {
            ++count;
            binding.editTextone.setText("" + numberOption);
            return;
        }
        if (TextUtils.isEmpty(binding.editTextTwo.getText().toString())) {
            ++count;
            binding.editTextTwo.setText("" + numberOption);
            return;
        }
        if (TextUtils.isEmpty(binding.editTextthree.getText().toString())) {
            binding.editTextthree.setText("" + numberOption);
            ++count;
            return;
        }
        if (TextUtils.isEmpty(binding.editTextfour.getText().toString())) {
            binding.editTextfour.setText("" + numberOption);
            ++count;
            if (count == 4)
                verifyOTP();
        }
    }

    private void verifyOTP() {
        Utils.showProgressBar(this);
        LoginPresenter loginPresenter = new LoginPresenter(this);
        OTPReqeust otpReqeust = new OTPReqeust();
        otpReqeust.setMobile(user_mobile);
//        otpReqeust.setOtp(binding.editTextone.getText().toString() + "" + binding.editTextTwo.getText().toString() + ""
//                + binding.editTextthree.getText().toString() + "" + binding.editTextfour.getText().toString());
        otpReqeust.setOtp(binding.pinEnterView.getText().toString());
        loginPresenter.onValidateOTPRequest(otpReqeust);
    }

    public void onSuccessValidateOTP(APIResponse apiResponse) {

        Utils.hideProgressBar();
        if (apiResponse.getCode() == 200) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("new_password", new_password);
            intent.putExtra("user_mobile", user_mobile);
            intent.putExtra("isFromOtp", true);
            startActivity(intent);
            finish();
        }

    }

    public void onFailureValidateOTP(APIResponse apiResponse) {
        Utils.hideProgressBar();
        binding.pinEnterView.setText("");
        Toast.makeText(this, apiResponse.getDescription(), Toast.LENGTH_SHORT).show();
    }


}
