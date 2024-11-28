package com.credoapp.parent.presenter;

import android.app.Activity;

import com.credoapp.parent.events.EventDetailsResponse;
import com.credoapp.parent.events.EventDisplayActivity;
import com.credoapp.parent.events.EventsActivity;
import com.credoapp.parent.events.EventsResponse;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.ClassTimeTableResponse;
import com.credoapp.parent.model.FeeRequest;
import com.credoapp.parent.model.MessageResponse;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.ui.FeedBackScreen;
import com.credoapp.parent.ui.MessagesScreen;
import com.credoapp.parent.ui.NoListedSchoolScreen;
import com.credoapp.parent.ui.ProfileScreen;
import com.credoapp.parent.ui.SupportScreen;
import com.credoapp.parent.ui.TimeTableScreen;
import com.google.gson.JsonObject;
import com.credoapp.parent.interactor.ParentInteractor;
import com.credoapp.parent.model.NotificationResponse;
import com.credoapp.parent.model.StudentsResponse;
import com.credoapp.parent.ui.ChangePasswordActivity;
import com.credoapp.parent.ui.FeesDetailsScreen;
import com.credoapp.parent.ui.NotificationsScreen;
import com.credoapp.parent.ui.ParentStudentsScreen;

public class ParentPresenter implements IParentPresenter {

    private ParentInteractor parentInteractor;
    private Activity activity;

    public ParentPresenter(Activity activity) {
        parentInteractor = new ParentInteractor(this);
        this.activity = activity;
    }

    @Override
    public void onGetParentStudents(JsonObject jsonObject) {
        parentInteractor.onCallParentStudents(jsonObject);
    }

    @Override
    public void onChangePassword(JsonObject jsonObject) {
        parentInteractor.onCallChangePassword(jsonObject);
    }

    @Override
    public void onSuccessChangePassword(JsonObject jsonObject) {
        ((ChangePasswordActivity) activity).onSuccessChangePassword(jsonObject);
    }

    @Override
    public void onSucessParentStudents(StudentsResponse studentsResponse) {
        ((ParentStudentsScreen) activity).onSuccessParentStudents(studentsResponse);
    }

    @Override
    public void onGetNotifications(JsonObject jsonObject) {
        parentInteractor.onCallNotifications(jsonObject);
    }

    @Override
    public void onSuccessNotifications(NotificationResponse notificationResponse) {
        ((NotificationsScreen) activity).onSuccessNotifications(notificationResponse);
    }

    @Override
    public void onRequestPayment(FeeRequest feeRequest) {
        parentInteractor.onCallPayment(feeRequest);
    }

    @Override
    public void onSuccessPayment(JsonObject jsonObject) {
        ((FeesDetailsScreen) activity).onSuccessFeePaid(jsonObject);
    }

    @Override
    public void onFailureParentStudents(StudentsResponse studentsResponse) {
        ((ParentStudentsScreen) activity).onFailureParentStudents(studentsResponse);
    }

    @Override
    public void onRequestNotListedSchool(JsonObject jsonObject) {
        parentInteractor.onCallNotListedSchool(jsonObject);
    }

    @Override
    public void onSuccessNotListedSchool(APIResponse apiResponse) {
        ((NoListedSchoolScreen) activity).onSuccessNotListedSchool(apiResponse);
    }

    @Override
    public void onSendDeviceKey(JsonObject jsonObject) {
        parentInteractor.onCallSendDeviceToken(jsonObject);
    }

    @Override
    public void onSuccessDeviceKey(JsonObject jsonObject) {
        ((ParentStudentsScreen) activity).onSuccessParentDeviceToken(jsonObject);
    }

    @Override
    public void onSendFeedback(JsonObject jsonObject) {
        parentInteractor.onCallSendFeedBack(jsonObject);
    }

    @Override
    public void onSuccessFeedback(JsonObject jsonObject) {
        ((FeedBackScreen) activity).onSuccessFeedback(jsonObject);
    }

    @Override
    public void onFailureFeedback(String message) {

    }

    @Override
    public void onSendSupportRequest(JsonObject jsonObject) {
        parentInteractor.onCallSendRequestSupport(jsonObject);
    }

    @Override
    public void onSuccessSupportRequest(JsonObject jsonObject) {
        ((SupportScreen) activity).onSuccessSupportRequest(jsonObject);
    }

    @Override
    public void onFailureSupportRequest(String message) {

    }

    @Override
    public void onGetStudentParentProfile(JsonObject jsonObject) {
        parentInteractor.onCallGetProfile(jsonObject);
    }

    @Override
    public void onSuccessStudentParentProfile(ParentStudentResponse parentStudentResponse) {
        ((ProfileScreen) activity).onSuccessParentStudentProfile(parentStudentResponse);
    }


    @Override
    public void onFailureStudentParentProfile(String message) {

    }

    @Override
    public void onRequestParentMessages(JsonObject jsonObject) {
        parentInteractor.onCallParentMessages(jsonObject);
    }

    @Override
    public void onFailureParentMessages(String message) {

    }

    @Override
    public void onSuccessParentMessages(MessageResponse messageResponse) {
        ((MessagesScreen) activity).onSuccessParentMessages(messageResponse);
    }

    @Override
    public void onRequestEvents(JsonObject jsonObject) {
        parentInteractor.onCallGetEvents(jsonObject);
    }

    @Override
    public void onSuccessEvents(EventsResponse eventsResponse) {
        ((EventsActivity) activity).onSuccessEvents(eventsResponse);
    }

    @Override
    public void onFailureEvents(String message) {

    }

    @Override
    public void onRequestEventsDetails(JsonObject jsonObject) {
        parentInteractor.onCallGetEventDetails(jsonObject);
    }

    @Override
    public void onSuccessEventDetails(EventDetailsResponse eventDetailsResponse) {
        ((EventDisplayActivity) activity).onSuccessEventDetails(eventDetailsResponse);
    }

    @Override
    public void onFailureEventDetails(String message) {

    }


    public void onSuccessTimeTable(ClassTimeTableResponse classTimeTableResponse) {
        ((TimeTableScreen) activity).onSuccessTimeTable(classTimeTableResponse);
    }

    public void onGetTimeTableDummy(JsonObject jsonObject) {
        parentInteractor.onCallTimeTable(jsonObject);
    }
}
