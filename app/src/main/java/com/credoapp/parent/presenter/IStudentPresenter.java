package com.credoapp.parent.presenter;

import com.credoapp.parent.model.AttendanceReportResponse;
import com.credoapp.parent.model.AttendanceResponse;
import com.credoapp.parent.model.FeeHistoryResponse;
import com.google.gson.JsonObject;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.AssignmentResponse;
import com.credoapp.parent.model.ExamDateResponse;
import com.credoapp.parent.model.ExamResultsResponse;
import com.credoapp.parent.model.FeesResponse;
import com.credoapp.parent.model.PasswordResponse;

public interface IStudentPresenter {

    void onRequestAssigments(JsonObject jsonObject);

    void onSuccessAssignments(AssignmentResponse assignmentResponse);

    void onRequestFeeDetails(JsonObject jsonObject);

    void onSuccessFeeDetails(FeesResponse feesResponse);

    void onRequestExamTimeTable(String studentID);

    void onSuccessExamTimeTable(ExamDateResponse examDateResponse);

    void onResendOTP(JsonObject resendOTPObject);

    void onSuccessResendOTP(APIResponse apiResponse);

    void onFailureResendOTP(JsonObject jsonObject);

    void onResetPassword(JsonObject jsonObject);

    void onSuccessResetPassword(PasswordResponse apiResponse);

    void onExamResults();

    void onSuccessExamResults(ExamResultsResponse examResultsResponse);

    void onRequestAttendance(JsonObject jsonObject);

    void onSuccessAttendance(AttendanceResponse attendanceResponse);

    void onRequestFeeHistory(JsonObject jsonObject);

    void onSuccessFeeHistory(FeeHistoryResponse feeHistoryResponse);

    void onFailureFeeHistory(String message);

    void onGetLastThreeMonthsAttedanceReport(JsonObject jsonObject);

    void onSuccessLastThreeMonthsAttendanceReport(AttendanceReportResponse attendanceReportResponse);

    void onFailureLastThreeMonthsAttendanceReport(String message);
}
