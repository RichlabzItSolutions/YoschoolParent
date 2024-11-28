package com.credoapp.parent.presenter;

import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.model.OTPReqeust;

public interface ILoginPresenter {

    public void onVerifyMobile(LoginRequest loginRequest);

    public void onSuccessVerify(LoginResponse loginResponse);

    public void onValidateOTPRequest(OTPReqeust otpReqeust);

    void onSuccessValidateOTP(APIResponse apiResponse);

    void onFailureLogin(LoginResponse loginResponse);

    void onFailureLoginMessage(String message);

    void onLoginRequest(LoginRequest loginRequest);

    void onSuccessLoginResponse(LoginResponse loginResponse);

}
