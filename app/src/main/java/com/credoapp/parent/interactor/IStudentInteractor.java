package com.credoapp.parent.interactor;

import com.google.gson.JsonObject;

public interface IStudentInteractor {

    void onCallStudentAssignments(JsonObject jsonObject);

    void onCallStudentFees(JsonObject jsonObject);

    void onCallExamTimeTable(String studentId);

    void onCallExamResults();

    void onCallResendOTP(JsonObject jsonObject);

    void onCallResetPassword(JsonObject jsonObject);

    void onCallAttendance(JsonObject jsonObject);

    void onCallStudentFeeHistory(JsonObject jsonObject);

    void onCallGetLastThreeMonthsAttendanceData(JsonObject jsonObject);
}
