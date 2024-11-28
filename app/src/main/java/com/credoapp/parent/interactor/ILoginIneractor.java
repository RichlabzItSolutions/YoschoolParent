package com.credoapp.parent.interactor;

import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.OTPReqeust;

public interface ILoginIneractor {

    public void onCallVerifyMobile(LoginRequest loginRequest);

    void onCallLoginRequest(LoginRequest loginRequest);

    public void onCallValidateOTP(OTPReqeust otpReqeust);
}
