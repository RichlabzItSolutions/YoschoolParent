package com.credoapp.parent.network;

import com.credoapp.parent.common.Urls;
import com.credoapp.parent.events.EventDetailsRequest;
import com.credoapp.parent.events.EventDetailsResponse;
import com.credoapp.parent.events.EventsRequest;
import com.credoapp.parent.events.EventsResponse;
import com.credoapp.parent.model.AttendanceReportResponse;
import com.credoapp.parent.model.AttendanceResponse;
import com.credoapp.parent.model.ClassTimeTableResponse;
import com.credoapp.parent.model.FeeHistoryResponse;
import com.credoapp.parent.model.FeeRequest;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.MessageResponse;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.model.UpdateParentModel.UpdateParentRequest;
import com.credoapp.parent.model.addAssignment.AccademicYearRequest;
import com.credoapp.parent.model.addAssignment.AccademicyearResponse;
import com.credoapp.parent.model.addAssignment.AddAssignmentResponse;
import com.credoapp.parent.model.addAssignment.DeleteAssignmentImageRequest;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesRequest;
import com.credoapp.parent.model.addAssignment.GetAddedAssignmentImagesResponse;
import com.credoapp.parent.model.addAssignment.RepliedAssignmentRequest;
import com.credoapp.parent.model.appVersionModels.AppVersionResponse;
import com.credoapp.parent.model.attendanceModels.ThreeMonthsDataRequest;
import com.credoapp.parent.model.attendanceModels.ThreeMonthsDataResponse;
import com.credoapp.parent.model.busNotRequiredModels.BusNOtRequiredRequest;
import com.credoapp.parent.model.busTracking.RandomLocationResponse;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineRequest;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineResponse;
import com.credoapp.parent.model.donateBooks.DonateBooksGetRequiredRequest;
import com.credoapp.parent.model.donateBooks.DonateBooksGetRequiredResponse;
import com.credoapp.parent.model.donateBooks.SubmitDonateRequest;
import com.credoapp.parent.model.donateBooks.SubmitDonateResponse;
import com.credoapp.parent.model.donateBooks.UpdateDonateBooksRequest;
import com.credoapp.parent.model.donateBooks.UpdateDonateBooksResponse;
import com.credoapp.parent.model.donateListModels.DeleteRequest;
import com.credoapp.parent.model.donateListModels.DeleteResponse;
import com.credoapp.parent.model.donateListModels.DonateCallRequest;
import com.credoapp.parent.model.donateListModels.DonateCallResponse;
import com.credoapp.parent.model.donateListModels.DonateListRequest;
import com.credoapp.parent.model.donateListModels.DonateListResponse;
import com.credoapp.parent.model.driverDetails.DriverRequest;
import com.credoapp.parent.model.examsModels.ExamDateRequest;
import com.credoapp.parent.model.feeModels.DiscountStatusRequest;
import com.credoapp.parent.model.feeModels.DiscountStatusResponse;
import com.credoapp.parent.model.feeModels.FeeModelsRequest;
import com.credoapp.parent.model.feeModels.FeeModelsResponse;
import com.credoapp.parent.model.getUpdeteDonationInfo.GetBooksDonationInfoRequest;
import com.credoapp.parent.model.getUpdeteDonationInfo.GetBooksDonationInfoResponse;
import com.credoapp.parent.model.holidaysModels.HolidaysListRequest;
import com.credoapp.parent.model.holidaysModels.HolidaysListResponse;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListRequest;
import com.credoapp.parent.model.monthlySllabusModels.GetMonthlySyllabusListResponse;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassStatusRequest;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesRequest;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesResponse;
import com.credoapp.parent.model.otpModels.ResendOtpRequest;
import com.credoapp.parent.model.otpModelsForNumber.OtpRequest;
import com.credoapp.parent.model.paymentModels.PaymentRequestIds;
import com.credoapp.parent.model.pdfModels.PdfRequest;
import com.credoapp.parent.model.pdfModels.PdfResponse;
import com.credoapp.parent.model.timeTableModels.TimeTableRequest;
import com.credoapp.parent.model.timeTableModels.TimeTableResponse;
import com.credoapp.parent.model.updateStudentModels.UpdateStudentRequest;
import com.credoapp.parent.model.vaccineModels.VaccineModelResponse;
import com.google.gson.JsonObject;
import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.model.AssignmentResponse;
import com.credoapp.parent.model.ExamDateResponse;
import com.credoapp.parent.model.ExamResultsResponse;
import com.credoapp.parent.model.FeesResponse;
import com.credoapp.parent.model.LoginRequest;
import com.credoapp.parent.model.LoginResponse;
import com.credoapp.parent.model.NotificationResponse;
import com.credoapp.parent.model.OTPReqeust;
import com.credoapp.parent.model.PasswordResponse;
import com.credoapp.parent.model.StudentsResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ParentAPI {
    @POST("parentinfo/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("parentinfo/verifyOTP")
    Call<APIResponse> verifyOTP(@Body OTPReqeust otpReqeust);

//    @POST("parentinfo/get_students")
//    Call<StudentsResponse> getParentStudents(@Body JsonObject jsonObject);

    @POST("parentinfo/get_students_after_login")
    Call<StudentsResponse> getParentStudents(@Body JsonObject jsonObject);

    @POST("student_assignments/student_assignments_list")
    Call<AssignmentResponse> getStudentAssignments(@Body JsonObject jsonObject);

    @POST("fees/getFeesDetails")
    Call<FeesResponse> getFeeDetails(@Body JsonObject jsonObject);

    @POST("Parentinfo/sendFeedback")
    Call<JsonObject> sendFeedBack(@Body JsonObject jsonObject);

    @POST("Parentinfo/sendRequestForSupport")
    Call<JsonObject> sendRequestForSupport(@Body JsonObject jsonObject);

    @POST("fees/getStudentFeePaidHistory")//fees/studentFeePaymentHistory
    Call<FeeHistoryResponse> getFeeHistory(@Body JsonObject jsonObject);

    @POST("parentinfo/changePassword")
    Call<JsonObject> changePassword(@Body JsonObject jsonObject);

    //http://credoapp.in/api/student_attendance/attendance
    @POST("student_attendance/attendance")
    Call<AttendanceResponse> getStudentAttendance(@Body JsonObject jsonObject);

    //    http://credoapp.in/api/exams/examResults
    @GET("exams/examResults")
    Call<ExamResultsResponse> getExamResults();

    //http://credoapp.in/api/exams/examDates
    @GET("exams/examDates")
    Call<ExamDateResponse> getExamTimeTable();

    @POST("Parent_Notification/parentNotifications")
    Call<NotificationResponse> getParentNotifications(@Body JsonObject jsonObject);

    @POST("parentinfo/resendOTP")
    Call<APIResponse> resendOTP(@Body JsonObject jsonObject);

    @POST("parentinfo/resetPassword")
    Call<PasswordResponse> resetPassword(@Body JsonObject jsonObject);

//    @POST("fees/payment")
//    Call<JsonObject> capturePayment(@Body JsonObject jsonObject);

    @POST("fees/studentFeePayment")
    Call<JsonObject> saveStudentPayment(@Body FeeRequest feeRequest);

    @POST("parentinfo/login_password")
    Call<LoginResponse> loginPassword(@Body LoginRequest loginRequest);

    @POST("parentinfo/notlisted_school")
    Call<APIResponse> noListedSchool(@Body JsonObject jsonObject);

    @POST("parentinfo/send_device_token")
    Call<JsonObject> sendDeviceToken(@Body JsonObject jsonObject);


    @POST("student_attendance/threeMonthsAttendanceReport")
    Call<AttendanceReportResponse> lastThreeMonthsAttendanceReport(@Body JsonObject jsonObject);

    @POST("Parentinfo/getProfile")
    Call<ParentStudentResponse> getStudentParentProfile(@Body JsonObject jsonObject);

    @POST("Parent_Notification/parentSMS")
    Call<MessageResponse> parentMessages(@Body JsonObject jsonObject);

    //    https://credoapp.in/api/Parent_Notification/classRoutine
    @POST("Parent_Notification/classRoutine")
    Call<ClassTimeTableResponse> classTimeTable(@Body JsonObject jsonObject);

    //    https://credoapp.in/timetable.php
//    https://credoapp.in/api/Parent_Notification/classRoutine
//    @POST("Parent_Notification/classRoutine")
//    Call<ClassTimeTableResponse> dummyTimeTable(@Body JsonObject jsonObject);

    @POST("events/getEvents")
    Call<EventsResponse> getEvents(@Body JsonObject jsonObject);

    @POST("events/eventDetails")
    Call<EventDetailsResponse> eventDetails(@Body JsonObject jsonObject);

    @POST("parentinfo/update_students_track")
    Call<EventDetailsResponse> updateStudentTrack(@Body JsonObject jsonObject);


    @POST("student_attendance/threeMonthsAttendanceReport")
    Call<ThreeMonthsDataResponse> lastThreeMonthsAttendanceResponse(@Body ThreeMonthsDataRequest request);


    @POST("parentinfo/update_students_track")
    Call<APIResponse> updatePaymentIds(@Body PaymentRequestIds request);

    @GET(Urls.GET_APP_VERSION)
    Call<AppVersionResponse> appVersionNo();

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> mondayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> tuesdayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> wednesdayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> thursdayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> frydayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<ClassRoutineResponse> saturdayClassRoutine(@Body ClassRoutineRequest request);

    @POST(Urls.DELETE_DONATION)
    Call<DeleteResponse> deleteDonation(@Body DeleteRequest request);

    @POST(Urls.CALL_DONATION)
    Call<DonateCallResponse> callDonation(@Body DonateCallRequest request);

    @POST(Urls.DONATE_BOOKS_LIST)
    Call<DonateListResponse> donateBooksList(@Body DonateListRequest request);

    @POST(Urls.UPDATE_DONATION_BOOKS_GET_INFO)
    Call<GetBooksDonationInfoResponse> getInfoBooks(@Body GetBooksDonationInfoRequest request);
    @POST(Urls.UPDATE_ONLINE_CLASS)
    Call<GlobalResponse> updateNewOnlineClassStatus(@Body OnlineClassStatusRequest request);

    @POST(Urls.UPDATE_DONATION_BOOKS)
    Call<UpdateDonateBooksResponse> updateDonateBooks(@Body UpdateDonateBooksRequest request);

    @POST(Urls.SUBMIT_DONATE_BOOKS)
    Call<SubmitDonateResponse> submitDonateResponse(@Body SubmitDonateRequest request);

    @POST(Urls.GET_CLASSES_SYLLABUS)
    Call<DonateBooksGetRequiredResponse> getClassesSyllabus(@Body DonateBooksGetRequiredRequest request);

    @POST(Urls.GET_PDFS_LIST)
    Call<PdfResponse> pdfsList(@Body PdfRequest request);

    @POST(Urls.TIME_TABLE_LIST)
    Call<TimeTableResponse> tableList(@Body TimeTableRequest request);

    @POST(Urls.GET_FEE_DATA)
    Call<FeeModelsResponse> getPaymentsRsponse(@Body FeeModelsRequest request);

    @GET(Urls.GET_EXAMS_DATA)
    Call<ExamDateResponse> getExamsRsults();


    @GET(Urls.GET_RANDOM_LOCATION)
    Call<RandomLocationResponse> getRandomLocaations();

    @POST(Urls.GET_MONTHLY_SYLLABUS_DATA)
    Call<GetMonthlySyllabusListResponse> syllabusList(@Body GetMonthlySyllabusListRequest request);

    @POST("parentinfo/resendOTP")
    Call<GlobalResponse> resendOtpRe(@Body ResendOtpRequest request);

    @POST(Urls.GET_HOLIDAYS_LIST)
    Call<HolidaysListResponse> getHolidaysList(@Body HolidaysListRequest request);

    @POST(Urls.GET_ROUTE)
    Call<GlobalResponse> getRoute(@Body DriverRequest request);

    @POST(Urls.GET_EXAMS_DATA)
    Call<ExamDateResponse> getExamsResults(@Body ExamDateRequest request);

    @POST(Urls.EVENTS_DETAILS)
    Call<EventDetailsResponse> getEventsDetails(@Body EventDetailsRequest request);

    @POST(Urls.EVENTS)
    Call<EventsResponse> getEventss(@Body EventsRequest request);

    @POST(Urls.UPDATE_STUDENT)
    Call<GlobalResponse> updateStudent(@Body UpdateStudentRequest request);

    @POST(Urls.GET_DISCOUNT_STATUS)
    Call<DiscountStatusResponse> getDiscountStatus(@Body DiscountStatusRequest request);

    @GET(Urls.GET_VACCINES)
    Call<VaccineModelResponse> getVaccinesList();

    @POST(Urls.UPDATE_PARENT_INFO)
    Call<GlobalResponse> updateParentInfo(@Body UpdateParentRequest request);

    @POST(Urls.BUS_NOT_REQUIRES_STATUS)
    Call<GlobalResponse> setBusNotRequired(@Body BusNOtRequiredRequest request);

    @POST(Urls.OTP_VERIFICATION)
    Call<GlobalResponse> verifyOtpNumberChange(@Body OtpRequest request);

    @POST(Urls.ONLINE_CLASSES_DATA)
    Call<OnlineClassesResponse> getOnLineClassesData(@Body OnlineClassesRequest request);

    @Multipart
    @POST(Urls.ADD_ASSIGNMENT_PHOTO)
    Call<AddAssignmentResponse> addAssignmentImage(@Part("addImage") RequestBody json, @Part MultipartBody.Part file);

    @POST(Urls.DELETE_ASSIGNMENT_PHOTO)
    Call<GlobalResponse> deleteAssignmentImage(@Body DeleteAssignmentImageRequest request);
    @POST(Urls.REPLIED_VIEW_ASSIGNMENT)
    Call<GetAddedAssignmentImagesResponse> repliedAssignments(@Body RepliedAssignmentRequest request);

    @POST(Urls.GET_ADDED_ASSIGNMENT_PHOTO)
    Call<GetAddedAssignmentImagesResponse> getAddedAssignmentImages(@Body GetAddedAssignmentImagesRequest request);

    @POST(Urls.GET_ACADEMICYEARS)
    Call<AccademicyearResponse> getAcademicyears(@Body AccademicYearRequest request);


  



}
