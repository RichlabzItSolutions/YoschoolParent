package com.credoapp.parent.presenter;

import com.credoapp.parent.events.EventDetailsResponse;
import com.credoapp.parent.events.EventsResponse;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.FeeRequest;
import com.credoapp.parent.model.MessageResponse;
import com.credoapp.parent.model.ParentStudentResponse;
import com.google.gson.JsonObject;
import com.credoapp.parent.model.NotificationResponse;
import com.credoapp.parent.model.StudentsResponse;

public interface IParentPresenter {

    void onGetParentStudents(JsonObject jsonObject);

    void onChangePassword(JsonObject jsonObject);

    void onSuccessChangePassword(JsonObject jsonObject);

    void onSucessParentStudents(StudentsResponse studentsResponse);

    void onGetNotifications(JsonObject jsonObject);

    void onSuccessNotifications(NotificationResponse notificationResponse);

    void onRequestPayment(FeeRequest feeRequest);

    void onSuccessPayment(JsonObject jsonObject);

    void onFailureParentStudents(StudentsResponse studentsResponse);

    void onRequestNotListedSchool(JsonObject jsonObject);

    void onSuccessNotListedSchool(APIResponse apiResponse);

    void onSendDeviceKey(JsonObject jsonObject);

    void onSuccessDeviceKey(JsonObject jsonObject);


    void onSendFeedback(JsonObject jsonObject);

    void onSuccessFeedback(JsonObject jsonObject);

    void onFailureFeedback(String message);

    void onSendSupportRequest(JsonObject jsonObject);

    void onSuccessSupportRequest(JsonObject jsonObject);

    void onFailureSupportRequest(String message);

    void onGetStudentParentProfile(JsonObject jsonObject);

    void onSuccessStudentParentProfile(ParentStudentResponse parentStudentResponse);

    void onFailureStudentParentProfile(String message);

    void onRequestParentMessages(JsonObject jsonObject);

    void onFailureParentMessages(String message);

    void onSuccessParentMessages(MessageResponse messageResponse);


    void onRequestEvents(JsonObject jsonObject);

    void onSuccessEvents(EventsResponse eventsResponse);

    void onFailureEvents(String message);

    void onRequestEventsDetails(JsonObject jsonObject);

    void onSuccessEventDetails(EventDetailsResponse eventDetailsResponse);

    void onFailureEventDetails(String message);
}
