package com.credoapp.parent.presenter;

import android.app.Activity;

import com.credoapp.parent.interactor.LoginInteractor;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.model.OTPReqeust;
import com.credoapp.parent.ui.LoginPasswordScreen;
import com.credoapp.parent.ui.LoginScreen;
import com.credoapp.parent.ui.OTPScreen;

public class LoginPresenter implements ILoginPresenter {


    private LoginInteractor loginInteractor;
    private Activity activity;

    public LoginPresenter(Activity activity) {
        this.activity = activity;
        loginInteractor = new LoginInteractor(this);
    }


    @Override
    public void onVerifyMobile(LoginRequest loginRequest) {
        loginInteractor.onCallVerifyMobile(loginRequest);
    }

    @Override
    public void onSuccessVerify(LoginResponse loginResponse) {
        ((LoginScreen) activity).onSuccessLogin(loginResponse);
    }

    @Override
    public void onValidateOTPRequest(OTPReqeust otpReqeust) {
        loginInteractor.onCallValidateOTP(otpReqeust);
    }

    @Override
    public void onSuccessValidateOTP(APIResponse apiResponse) {
        ((OTPScreen) activity).onSuccessValidateOTP(apiResponse);
    }

    @Override
    public void onFailureLogin(LoginResponse loginResponse) {
        if (activity instanceof LoginScreen)
            ((LoginScreen) activity).onFailureLogin(loginResponse);
        else
            ((LoginPasswordScreen) activity).onFailureLogin(loginResponse);
    }

    @Override
    public void onFailureLoginMessage(String message) {
        ((LoginScreen) activity).onFailureLoginMessage(message);
    }

    @Override
    public void onLoginRequest(LoginRequest loginRequest) {
        loginInteractor.onCallLoginRequest(loginRequest);
    }

    @Override
    public void onSuccessLoginResponse(LoginResponse loginResponse) {
        ((LoginPasswordScreen) activity).onSuccessLogin(loginResponse);
    }

    public void onFailureValidateOTP(APIResponse body) {
        ((OTPScreen) activity).onFailureValidateOTP(body);
    }
}
