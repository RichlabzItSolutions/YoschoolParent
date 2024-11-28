package com.credoapp.parent.common;

public class Urls {


    public static final String BASE_URL_PARENT ="https://yoskool.com/api/";

    public static final String GET_APP_VERSION = BASE_URL_PARENT + "Parent_Notification/force_update";

    public static final String LOGIN_= BASE_URL_PARENT +"/parents/login?";

    public static final String CHANGE_PWD_= BASE_URL_PARENT +"parents/change_password?";

    public static final String SIGN_UP_= BASE_URL_PARENT +"parents/register";

    public static final String OTP_SIGN_UP= BASE_URL_PARENT +"parents/user_account_activate?";

    public static final String RESEND_OTP_= BASE_URL_PARENT +"parents/resend_otp?";

    public static final String FORGOT_PASSWORD= BASE_URL_PARENT +"parents/forgotPassword?";

    public static final String VERIFY_OTP_FOR_FORGOT_PASSWORD= BASE_URL_PARENT +"parents/verifyOTP?";

    public static final String RESET_PASSWORD= BASE_URL_PARENT +"parents/resetPassword";

    public static final String GET_LEARNING_DATA= BASE_URL_PARENT +"parents/postLearninData?";

    public static final String DELETE_DONATION = BASE_URL_PARENT +"student/deleteBooksDonation";

    public static final String CALL_DONATION = BASE_URL_PARENT +"student/donationCalling";

    public static final String DONATE_BOOKS_LIST = BASE_URL_PARENT +"student/booksDonationList?";

    public static final String UPDATE_DONATION_BOOKS_GET_INFO = BASE_URL_PARENT +"student/getBooksDonation";

    public static final String UPDATE_ONLINE_CLASS = BASE_URL_PARENT +"student/updateNewOnlineClassStatus";


    public static final String UPDATE_DONATION_BOOKS = BASE_URL_PARENT +"student/updateBooksDonation";

    public static final String SUBMIT_DONATE_BOOKS = BASE_URL_PARENT +"student/insertDonateBooks?";

    public static final String GET_CLASSES_SYLLABUS = BASE_URL_PARENT +"student/donateBooks";

    public static final String GET_PDFS_LIST = BASE_URL_PARENT + "Parentinfo/parent_pdf";

    public static final String TIME_TABLE_LIST = BASE_URL_PARENT + "admin/get_timetable";

    public static final String GET_FEE_DATA = BASE_URL_PARENT + "fees/getStudentFeeDetailsnew";

    public static final String GET_EXAMS_DATA = BASE_URL_PARENT + "exams/get_student_exam_result";

    public static final String GET_MONTHLY_SYLLABUS_DATA = BASE_URL_PARENT + "parentinfo/get_monthly_syllabus_list";

    public static final String GET_HOLIDAYS_LIST = BASE_URL_PARENT + "parentinfo/get_holidays_list";

    public static final String GET_RANDOM_LOCATION = BASE_URL_PARENT +"driver/get_bustrack_details";

    public static final String GET_ROUTE = BASE_URL_PARENT +"driver/get_student_route";

    public static final String EVENTS_DETAILS = BASE_URL_PARENT +"events/eventDetails";

    public static final String EVENTS = BASE_URL_PARENT +"events/getEvents";

    public static final String UPDATE_STUDENT =BASE_URL_PARENT +"student/update_student" ;

    public static final String GET_DISCOUNT_STATUS = BASE_URL_PARENT +"fees/getDiscountStatus" ;

    public static final String GET_VACCINES  = BASE_URL_PARENT +"parentinfo/vaccine_info";

    public static final String UPDATE_PARENT_INFO = BASE_URL_PARENT +"student/update_parent";

    public static final String BUS_NOT_REQUIRES_STATUS = BASE_URL_PARENT +"parentinfo/bus_not_required";

    public static final String OTP_VERIFICATION = BASE_URL_PARENT +"student/change_parent_mobile";

    public static final String ONLINE_CLASSES_DATA = BASE_URL_PARENT +"student/onlineClasses";

    public static final String ADD_ASSIGNMENT_PHOTO = BASE_URL_PARENT +"student_assignments/submitAssignment";

    public static final String DELETE_ASSIGNMENT_PHOTO = BASE_URL_PARENT +"student_assignments/assignmentImgDelete";

    public static final String GET_ADDED_ASSIGNMENT_PHOTO = BASE_URL_PARENT +"student_assignments/studentSubmittedAssignments";
    public static final String REPLIED_VIEW_ASSIGNMENT = BASE_URL_PARENT + "student_assignments/repliedAssignments";
    public static final String GET_ACADEMICYEARS = BASE_URL_PARENT + "teacher/getAcademicYears?";


    //https://credoapp.in/api/
}
