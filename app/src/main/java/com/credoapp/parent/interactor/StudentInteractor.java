package com.credoapp.parent.interactor;

import android.util.Log;

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
import com.credoapp.parent.network.ParentAPI;
import com.credoapp.parent.network.RetrofitImpl;
import com.credoapp.parent.presenter.StudentPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentInteractor implements IStudentInteractor {


    private static final String TAG = StudentInteractor.class.getSimpleName();
    private Retrofit retrofit;
    private RetrofitImpl retrofitImpl;
    private StudentPresenter studentPresenter;

    public StudentInteractor(StudentPresenter studentPresenter) {
        this.studentPresenter = studentPresenter;
        retrofitImpl = RetrofitImpl.getRetrofitImpl();
        retrofit = retrofitImpl.getRetrofit();
    }

    @Override
    public void onCallStudentAssignments(JsonObject jsonObject) {
        Call<AssignmentResponse> assignmentResponseCall = retrofit.create(ParentAPI.class).getStudentAssignments(jsonObject);
        assignmentResponseCall.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessAssignments(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onCallStudentFees(JsonObject jsonObject) {
        Call<FeesResponse> feeDetails = retrofit.create(ParentAPI.class).getFeeDetails(jsonObject);
        feeDetails.enqueue(new Callback<FeesResponse>() {
            @Override
            public void onResponse(Call<FeesResponse> call, Response<FeesResponse> response) {
                Log.d(TAG, "response " + response.isSuccessful());
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessFeeDetails(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<FeesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCallExamTimeTable(String studentId) {
        Call<ExamDateResponse> examTimeTable = retrofit.create(ParentAPI.class).getExamTimeTable();
        examTimeTable.enqueue(new Callback<ExamDateResponse>() {
            @Override
            public void onResponse(Call<ExamDateResponse> call, Response<ExamDateResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessExamTimeTable(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExamDateResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCallExamResults() {
        Call<ExamResultsResponse> examResults = retrofit.create(ParentAPI.class).getExamResults();
        examResults.enqueue(new Callback<ExamResultsResponse>() {
            @Override
            public void onResponse(Call<ExamResultsResponse> call, Response<ExamResultsResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessExamResults(response.body());
                }
            }
            @Override
            public void onFailure(Call<ExamResultsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallResendOTP(JsonObject jsonObject) {
        Call<APIResponse> resendOTP = retrofit.create(ParentAPI.class).resendOTP(jsonObject);
        resendOTP.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessResendOTP(response.body());
                }
            }
            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCallResetPassword(JsonObject jsonObject) {
        Call<PasswordResponse> apiResponseCall = retrofit.create(ParentAPI.class).resetPassword(jsonObject);
        apiResponseCall.enqueue(new Callback<PasswordResponse>() {
            @Override
            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessResetPassword(response.body());
                }
            }

            @Override
            public void onFailure(Call<PasswordResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallAttendance(JsonObject jsonObject) {
        Call<AttendanceResponse> studentAttendance = retrofit.create(ParentAPI.class).getStudentAttendance(jsonObject);
        studentAttendance.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessAttendance(response.body());
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCallStudentFeeHistory(JsonObject jsonObject) {
        Call<FeeHistoryResponse> feeHistory = retrofit.create(ParentAPI.class).getFeeHistory(jsonObject);

        feeHistory.enqueue(new Callback<FeeHistoryResponse>() {
            @Override
            public void onResponse(Call<FeeHistoryResponse> call, Response<FeeHistoryResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessFeeHistory(response.body());
                } else {
                    studentPresenter.onFailureFeeHistory("Please Try Again!");
                }
            }

            @Override
            public void onFailure(Call<FeeHistoryResponse> call, Throwable t) {
                studentPresenter.onFailureFeeHistory("Please Try Again!");
            }
        });
    }

    @Override
    public void onCallGetLastThreeMonthsAttendanceData(JsonObject jsonObject) {
        Call<AttendanceReportResponse> attendanceReport = retrofit.create(ParentAPI.class).lastThreeMonthsAttendanceReport(jsonObject);
        attendanceReport.enqueue(new Callback<AttendanceReportResponse>() {
            @Override
            public void onResponse(Call<AttendanceReportResponse> call, Response<AttendanceReportResponse> response) {
                if (response.isSuccessful()) {
                    studentPresenter.onSuccessLastThreeMonthsAttendanceReport(response.body());
                }
            }

            @Override
            public void onFailure(Call<AttendanceReportResponse> call, Throwable t) {

            }
        });
    }
}
