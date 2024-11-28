package com.credoapp.parent.interactor;

import android.util.Log;

import com.credoapp.parent.events.EventDetailsResponse;
import com.credoapp.parent.events.EventsResponse;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.ClassTimeTableResponse;
import com.credoapp.parent.model.FeeRequest;
import com.credoapp.parent.model.MessageResponse;
import com.credoapp.parent.model.NotificationResponse;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.model.StudentsResponse;
import com.credoapp.parent.network.ParentAPI;
import com.credoapp.parent.network.RetrofitImpl;
import com.credoapp.parent.presenter.ParentPresenter;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ParentInteractor implements IParentInteractor {

    private static final String TAG = ParentInteractor.class.getSimpleName();
    private Retrofit retrofit;
    private RetrofitImpl retrofitImpl;
    private ParentPresenter parentPresenter;

    public ParentInteractor(ParentPresenter parentPresenter) {
        this.parentPresenter = parentPresenter;
        retrofitImpl = RetrofitImpl.getRetrofitImpl();
        retrofit = retrofitImpl.getRetrofit();
    }

    @Override
    public void onCallParentStudents(JsonObject jsonObject) {
        Call<StudentsResponse> parentStudents = retrofit.create(ParentAPI.class).getParentStudents(jsonObject);
        parentStudents.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(Call<StudentsResponse> call, Response<StudentsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        parentPresenter.onSucessParentStudents(response.body());
                    } else {
                        parentPresenter.onFailureParentStudents(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallChangePassword(final JsonObject jsonObject) {
        Call<JsonObject> changePassword = retrofit.create(ParentAPI.class).changePassword(jsonObject);
        changePassword.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject passwordJsonObject = response.body();
                    Log.d(TAG, "response.body() :::::::::::::: " + passwordJsonObject);
                    if (passwordJsonObject.get("code").getAsString().equals("200")) {
                        parentPresenter.onSuccessChangePassword(passwordJsonObject);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCallNotifications(JsonObject jsonObject) {
        Call<NotificationResponse> notifications = retrofit.create(ParentAPI.class).getParentNotifications(jsonObject);
        notifications.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    NotificationResponse notificationResponse = response.body();
                    Log.d(TAG, "response.body() :::::::::::::: " + notificationResponse);
                    if (notificationResponse.getCode() == 200) {
                        parentPresenter.onSuccessNotifications(notificationResponse);
                    } else {
                        parentPresenter.onSuccessNotifications(notificationResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallPayment(final FeeRequest feeRequest) {
        Call<JsonObject> paymentCapture = retrofit.create(ParentAPI.class).saveStudentPayment(feeRequest);
        paymentCapture.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessPayment(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallNotListedSchool(JsonObject jsonObject) {
        Call<APIResponse> noListedSchool = retrofit.create(ParentAPI.class).noListedSchool(jsonObject);
        noListedSchool.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessNotListedSchool(response.body());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallSendDeviceToken(final JsonObject jsonObject) {
        Call<JsonObject> sendDeviceToken = retrofit.create(ParentAPI.class).sendDeviceToken(jsonObject);
        sendDeviceToken.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessDeviceKey(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallSendFeedBack(JsonObject jsonObject) {
        Call<JsonObject> sendFeedBack = retrofit.create(ParentAPI.class).sendFeedBack(jsonObject);
        sendFeedBack.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessFeedback(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallSendRequestSupport(JsonObject jsonObject) {
        Call<JsonObject> requestForSupport = retrofit.create(ParentAPI.class).sendRequestForSupport(jsonObject);
        requestForSupport.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessSupportRequest(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallGetProfile(JsonObject jsonObject) {
        Call<ParentStudentResponse> studentParentProfile = retrofit.create(ParentAPI.class).getStudentParentProfile(jsonObject);
        studentParentProfile.enqueue(new Callback<ParentStudentResponse>() {
            @Override
            public void onResponse(Call<ParentStudentResponse> call, Response<ParentStudentResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessStudentParentProfile(response.body());
                }
            }

            @Override
            public void onFailure(Call<ParentStudentResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallParentMessages(JsonObject jsonObject) {
        Call<MessageResponse> parentMessages = retrofit.create(ParentAPI.class).parentMessages(jsonObject);
        parentMessages.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessParentMessages(response.body());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallGetEvents(JsonObject jsonObject) {
        Call<EventsResponse> events = retrofit.create(ParentAPI.class).getEvents(jsonObject);
        events.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessEvents(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallGetEventDetails(JsonObject jsonObject) {
        Call<EventDetailsResponse> eventDetails = retrofit.create(ParentAPI.class).eventDetails(jsonObject);
        eventDetails.enqueue(new Callback<EventDetailsResponse>() {
            @Override
            public void onResponse(Call<EventDetailsResponse> call, Response<EventDetailsResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessEventDetails(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<EventDetailsResponse> call, Throwable t) {

            }
        });
    }

    public void onCallTimeTable(JsonObject jsonObject) {
        Call<ClassTimeTableResponse> timeTable = retrofit.create(ParentAPI.class).classTimeTable(jsonObject);

        timeTable.enqueue(new Callback<ClassTimeTableResponse>() {
            @Override
            public void onResponse(Call<ClassTimeTableResponse> call, Response<ClassTimeTableResponse> response) {
                if (response.isSuccessful()) {
                    parentPresenter.onSuccessTimeTable(response.body());
                }
            }

            @Override
            public void onFailure(Call<ClassTimeTableResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
