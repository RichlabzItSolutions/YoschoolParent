package com.credoapp.parent.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.LoginPresenter;
import com.credoapp.parent.utils.Utils;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityLoginPasswordBinding;

public class LoginPasswordScreen extends AppCompatActivity {
    ActivityLoginPasswordBinding binding;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_password);
        binding = (ActivityLoginPasswordBinding) viewDataBinding;
        username = getIntent().getStringExtra("username");

        binding.loginButton.setOnClickListener(v -> {
            if (!validateFields()) return;
            Utils.showProgressBar(LoginPasswordScreen.this);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setMobile(username);
            loginRequest.setPassword(binding.password.getText().toString());
            LoginPresenter loginPresenter = new LoginPresenter(LoginPasswordScreen.this);
            loginPresenter.onLoginRequest(loginRequest);
        });

        binding.forgotPwd.setOnClickListener(v -> startActivity(new Intent(LoginPasswordScreen.this, ResendOTPScreen.class)));

    }

    private boolean validateFields() {
        if (binding.password.getText().toString().isEmpty()) {
            errorValidityMessage("Please Enter Password.");
            return false;
        }
        return true;
    }

    private void errorValidityMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    public void onSuccessLogin(LoginResponse loginResponse) {
        Utils.hideProgressBar();
        SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
        sharedPref.saveParentId(loginResponse.getParentInfos().getParent_id());
        sharedPref.saveParentName(loginResponse.getParentInfos().getParent_name());
        sharedPref.saveMobileNumber(username);
        sharedPref.saveAdminID(loginResponse.getParentInfos().getAdmin_id());

        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        preferences.edit().putString(Constants.PARENT_ID, loginResponse.getParentInfos().getParent_id()).apply();
        preferences.edit().putString(Constants.PARENT_NAME, loginResponse.getParentInfos().getParent_name()).apply();
        preferences.edit().putString(Constants.ADMIN_ID, loginResponse.getParentInfos().getAdmin_id()).apply();
        preferences.edit().putString(Constants.PARENT_NUMBER, username).apply();


        if (loginResponse.getCode() == 200) {
            Intent intent = new Intent(LoginPasswordScreen.this, ParentStudentsScreen.class);
            startActivity(intent);
            finish();
        } else {
            errorValidityMessage(loginResponse.getDescription());
        }
    }

    public void onFailureLogin(LoginResponse loginResponse) {
        Utils.hideProgressBar();
        errorValidityMessage(loginResponse.getDescription());
    }
}
