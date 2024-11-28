package com.credoapp.parent.presenter;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.credoapp.parent.model.AttendanceReportResponse;
import com.credoapp.parent.model.AttendanceResponse;
import com.credoapp.parent.model.FeeHistoryResponse;
import com.credoapp.parent.ui.AttendanceScreen;
import com.credoapp.parent.ui.FeeHistoryScreen;
import com.google.gson.JsonObject;
import com.credoapp.parent.fragment.ExamDateFragment;
import com.credoapp.parent.fragment.ExamResultsFragment;
import com.credoapp.parent.interactor.StudentInteractor;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.AssignmentResponse;
import com.credoapp.parent.model.ExamDateResponse;
import com.credoapp.parent.model.ExamResultsResponse;
import com.credoapp.parent.model.FeesResponse;
import com.credoapp.parent.model.PasswordResponse;
import com.credoapp.parent.ui.AssignementsScreen;
import com.credoapp.parent.ui.FeesDetailsScreen;
import com.credoapp.parent.ui.ForgotPasswordActivity;
import com.credoapp.parent.ui.ResendOTPScreen;

public class StudentPresenter implements IStudentPresenter {
    private Fragment fragment;
    private StudentInteractor studentInteractor;
    private Activity activity;

    public StudentPresenter(Activity activity) {
        this.activity = activity;
        studentInteractor = new StudentInteractor(this);
    }

    public StudentPresenter(Fragment fragment) {
        this.fragment = fragment;
        studentInteractor = new StudentInteractor(this);
    }

    @Override
    public void onRequestAssigments(JsonObject jsonObject) {
        studentInteractor.onCallStudentAssignments(jsonObject);
    }

    @Override
    public void onSuccessAssignments(AssignmentResponse assignmentResponse) {
        ((AssignementsScreen) activity).onSuccessAssignments(assignmentResponse);
    }

    @Override
    public void onRequestFeeDetails(JsonObject jsonObject) {
        studentInteractor.onCallStudentFees(jsonObject);
    }

    @Override
    public void onSuccessFeeDetails(FeesResponse feesResponse) {
        ((FeesDetailsScreen) activity).onSuccessFeeDetails(feesResponse);
    }

    @Override
    public void onRequestExamTimeTable(String studentID) {
        studentInteractor.onCallExamTimeTable(studentID);
    }

    @Override
    public void onSuccessExamTimeTable(ExamDateResponse examTimeTableResponse) {
        ((ExamDateFragment) fragment).onSuccessExamTimeTable(examTimeTableResponse);
    }

    @Override
    public void onResendOTP(JsonObject resendOTPObject) {
        studentInteractor.onCallResendOTP(resendOTPObject);
    }

    @Override
    public void onSuccessResendOTP(APIResponse apiResponse) {
        ((ResendOTPScreen) activity).onSuccessResendOTP(apiResponse);
    }

    @Override
    public void onFailureResendOTP(JsonObject jsonObject) {

    }

    @Override
    public void onResetPassword(JsonObject jsonObject) {
        studentInteractor.onCallResetPassword(jsonObject);
    }

    @Override
    public void onSuccessResetPassword(PasswordResponse apiResponse) {
        ((ForgotPasswordActivity) activity).onSuccessForgotPassword(apiResponse);
    }

    @Override
    public void onExamResults() {
        studentInteractor.onCallExamResults();
    }

    @Override
    public void onSuccessExamResults(ExamResultsResponse examResultsResponse) {
        ((ExamResultsFragment) fragment).onSuccessExamResults(examResultsResponse);
    }

    @Override
    public void onRequestAttendance(JsonObject jsonObject) {
        studentInteractor.onCallAttendance(jsonObject);
    }

    @Override
    public void onSuccessAttendance(AttendanceResponse attendanceResponse) {
        ((AttendanceScreen) activity).onSuccessAttendance(attendanceResponse);
    }

    @Override
    public void onRequestFeeHistory(JsonObject jsonObject) {
        studentInteractor.onCallStudentFeeHistory(jsonObject);
    }

    @Override
    public void onSuccessFeeHistory(FeeHistoryResponse feeHistoryResponse) {
        ((FeeHistoryScreen) activity).onSuccessFeeHistory(feeHistoryResponse);
    }

    @Override
    public void onFailureFeeHistory(String message) {
        ((FeeHistoryScreen) activity).onFailureFeeHistory(message);
    }

    @Override
    public void onGetLastThreeMonthsAttedanceReport(JsonObject jsonObject) {
        studentInteractor.onCallGetLastThreeMonthsAttendanceData(jsonObject);
    }

    @Override
    public void onSuccessLastThreeMonthsAttendanceReport(AttendanceReportResponse attendanceReportResponse) {
        ((AttendanceScreen) activity).onSuccessLastThreeMonthsReports(attendanceReportResponse);
    }

    @Override
    public void onFailureLastThreeMonthsAttendanceReport(String message) {

    }
}
