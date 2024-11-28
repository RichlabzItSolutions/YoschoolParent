package com.credoapp.parent.interactor;

import com.credoapp.parent.model.FeeRequest;
import com.google.gson.JsonObject;

public interface IParentInteractor {

    void onCallParentStudents(JsonObject jsonObject);

    void onCallChangePassword(JsonObject jsonObject);

    void onCallNotifications(JsonObject jsonObject);

    void onCallPayment(FeeRequest feeRequest);

    void onCallNotListedSchool(JsonObject jsonObject);

    void onCallSendDeviceToken(JsonObject jsonObject);

    void onCallSendFeedBack(JsonObject jsonObject);

    void onCallSendRequestSupport(JsonObject jsonObject);

    void onCallGetProfile(JsonObject jsonObject);

    void onCallParentMessages(JsonObject jsonObject);

    void onCallGetEvents(JsonObject jsonObject);

    void onCallGetEventDetails(JsonObject jsonObject);

}
