package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.google.gson.JsonObject;
import com.credoapp.parent.databinding.ResendOtpLayoutBinding;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.utils.Utils;

public class ResendOTPScreen extends AppCompatActivity {
    private ResendOtpLayoutBinding binding;
    StudentPresenter studentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.resend_otp_layout);
        binding = (ResendOtpLayoutBinding) viewDataBinding;
        studentPresenter = new StudentPresenter(this);
        binding.loginButton.setOnClickListener(v -> {
            if (!validateFields()) return;
            Utils.showProgressBar(ResendOTPScreen.this);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobile", binding.username.getText().toString());
            studentPresenter.onResendOTP(jsonObject);
        });


    }

    private boolean validateFields() {
        if (binding.username.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter No.", Toast.LENGTH_SHORT).show();
            return false;
        }if (!(binding.username.getText().toString().matches(Constants.MobilePattern))){
            Toast.makeText(this, "Enter valid mobile No.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onSuccessResendOTP(APIResponse apiResponse) {
        Utils.hideProgressBar();
        if (apiResponse.getCode() == 200) {
            Intent intent = new Intent(ResendOTPScreen.this, OTPScreen.class);
            intent.putExtra("isFromResend", true);
            intent.putExtra("new_password", true);
            intent.putExtra("user_mobile", binding.username.getText().toString());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, apiResponse.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }
}
