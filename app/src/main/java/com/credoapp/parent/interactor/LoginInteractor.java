package com.credoapp.parent.interactor;

import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.model.OTPReqeust;
import com.credoapp.parent.network.ParentAPI;
import com.credoapp.parent.network.RetrofitImpl;
import com.credoapp.parent.presenter.LoginPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginInteractor implements ILoginIneractor {

    private Retrofit retrofit;
    private RetrofitImpl retrofitImpl;
    private LoginPresenter loginPresenter;

    public LoginInteractor(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        retrofitImpl = RetrofitImpl.getRetrofitImpl();
        retrofit = retrofitImpl.getRetrofit();
    }


    @Override
    public void onCallVerifyMobile(LoginRequest loginRequest) {
        Call<LoginResponse> loginCall = retrofit.create(ParentAPI.class).login(loginRequest);
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        loginPresenter.onSuccessVerify(response.body());
                    } else {
                        loginPresenter.onFailureLogin(response.body());
                    }
                } else {
                    loginPresenter.onFailureLoginMessage("Invalid Credentials Please Try Again");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginPresenter.onFailureLoginMessage("Invalid Credentials Please Try Again");
            }
        });

    }

    @Override
    public void onCallLoginRequest(LoginRequest loginRequest) {
        Call<LoginResponse> loginResponseCall = retrofit.create(ParentAPI.class).loginPassword(loginRequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        loginPresenter.onSuccessLoginResponse(response.body());
                    } else {
                        loginPresenter.onFailureLogin(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallValidateOTP(OTPReqeust otpReqeust) {
        ParentAPI parentAPI = retrofit.create(ParentAPI.class);
        Call<APIResponse> apiResponseCall = parentAPI.verifyOTP(otpReqeust);
        apiResponseCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        loginPresenter.onSuccessValidateOTP(response.body());
                    }else {
                        loginPresenter.onFailureValidateOTP(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }
}
