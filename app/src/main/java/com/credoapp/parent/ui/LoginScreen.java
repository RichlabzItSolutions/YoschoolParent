package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.credoapp.parent.common.Constants;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityLoginBinding;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.presenter.LoginPresenter;
import com.credoapp.parent.utils.Utils;

public class LoginScreen extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding = (ActivityLoginBinding) viewDataBinding;
        binding.loginButton.setOnClickListener(v -> {
            if (!validateFields()) return;
            Utils.showProgressBar(LoginScreen.this);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setMobile(binding.username.getText().toString());
            // loginRequest.setPassword(binding.password.getText().toString());
            LoginPresenter loginPresenter = new LoginPresenter(LoginScreen.this);
            loginPresenter.onVerifyMobile(loginRequest);
        });
        binding.forgotPwd.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, ResendOTPScreen.class);
            startActivity(intent);
        });
    }

    private boolean validateFields() {
        if (binding.username.getText().toString().isEmpty()) {
            errorValidityMessage("Please Enter Mobile Number.");
            return false;
        }
        if (!(binding.username.getText().toString().matches(Constants.MobilePattern))){
            Toast.makeText(this, "Enter valid mobile No.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.username.getText().toString().length() < 10) {
            errorValidityMessage("Please Enter Valid Mobile Number.");
            return false;
        }

//        if (binding.password.getText().toString().isEmpty()) {
//            errorValidityMessage("Please Enter Password.");
//            return false;
//        }
        return true;
    }

    private void errorValidityMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    public void onSuccessLogin(LoginResponse loginResponse) {
        Utils.hideProgressBar();
        //Exists and verified
        if (loginResponse.getExits() == 1) {
            Intent intent = new Intent(LoginScreen.this, LoginPasswordScreen.class);
            intent.putExtra("username", binding.username.getText().toString());
            startActivity(intent);
        } else if (loginResponse.getExits() == 2) {
            //Parent is not registered need to take the information from the parent
            errorValidityMessage(loginResponse.getDescription());
        }
        finish();
    }

    public void onFailureLogin(LoginResponse loginResponse) {
        Utils.hideProgressBar();
        if (loginResponse.getExits() == 2) {
            //Parent is not registered need to take the information from the parent
//            errorValidityMessage(loginResponse.getDescription());
            Intent intent = new Intent(LoginScreen.this, NoListedSchoolScreen.class);
            intent.putExtra("user_mobile", binding.username.getText().toString());
            startActivity(intent);
        } else if (loginResponse.getExits() == 0) {
            //Exists but not verified need to launch the otp verification screen
            Intent intent = new Intent(LoginScreen.this, OTPScreen.class);
            intent.putExtra("new_password", true);
            intent.putExtra("user_mobile", binding.username.getText().toString());
            startActivity(intent);
            finish();
        }

    }

    public void onFailureLoginMessage(String message) {
        Utils.hideProgressBar();
        Snackbar.make(binding.getRoot(), "" + message, Snackbar.LENGTH_LONG).show();
    }
}
