package com.credoapp.parent.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.credoapp.parent.adapter.DashboardOptionsAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.R;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.StudentOption;
import com.credoapp.parent.ui.ProfileScreen;
import com.credoapp.parent.utils.IConstants;
import com.credoapp.parent.utils.SpacesItemDecoration;
import com.credoapp.parent.databinding.DashboardFragmentBinding;
import org.parceler.Parcels;
import java.util.ArrayList;
public class DashboardFragment extends Fragment {

    private DashboardFragmentBinding binding;
    ArrayList<StudentOption> studentOptions = new ArrayList<>();
    StudentInfo studentInfo;
    String userId;
    private ProgressDialog progress;
    private ArrayList<String> academicList, academicIdInList;
    private String adminid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,    @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
        binding = (DashboardFragmentBinding) viewDataBinding;
        studentInfo = Parcels.unwrap(getArguments().getParcelable("student_info"));
        Log.d("=======>", "onCreateView: dash       "+studentInfo);
        setStudentOptions();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        int spacingInPixels = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp);
        binding.studentOptionsRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        binding.studentOptionsRecyclerView.setLayoutManager(manager);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        preferences.edit().putString(Constants.USER_IDD, studentInfo.getStudent_id()).apply();
        adminid = preferences.getString(Constants.ADMIN_ID, adminid);

        userId = preferences.getString(Constants.USER_IDD, userId);
        Log.d("=======>", "onCreateView: dash       "+userId);

        DashboardOptionsAdapter dashboardOptionsAdapter = new DashboardOptionsAdapter(getActivity(), studentOptions, studentInfo);
        binding.studentOptionsRecyclerView.setAdapter(dashboardOptionsAdapter);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(getActivity()).setDefaultRequestOptions(requestOptions)
                .load(IConstants.IMAGE_BASE_URL + "" + studentInfo.student_photo).placeholder(R.drawable.yo_two).into(binding.studentImg);
        binding.studentName.setText(studentInfo.student_name);
        binding.studentClass.setText(studentInfo.class_name);
        binding.studentSchoolName.setText(studentInfo.school_name);

        final Intent intent = new Intent(getActivity(), ProfileScreen.class);
        intent.putExtra("student_info", Parcels.wrap(studentInfo));
        binding.studentImg.setOnClickListener(v -> startActivity(intent));
        binding.studentInfoView.setOnClickListener(v -> startActivity(intent));
        return binding.getRoot();

    }
    /*private void getAcademic() {
        showLoadingDialog();
        AccademicYearRequest request = new AccademicYearRequest();
        request.setAdmin_id(Integer.parseInt(adminid));//adminid
        if (Constants.haveInternet(getActivity())) {
            ITutorSource.getRestAPI().getAcademicyears(request).enqueue(new Callback<AccademicyearResponse>() {
                @Override
                public void onResponse(@NonNull Call<AccademicyearResponse> call, @NonNull Response<AccademicyearResponse> response) {
                    accademicResponse(Objects.requireNonNull(response.body()));


                }

                @Override
                public void onFailure(@NonNull Call<AccademicyearResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.IntenetSettings(getActivity());
        }
    }
    private void accademicResponse(AccademicyearResponse body) {
        dismissLoadingDialog();
        String response = body.getCode();
        String description = body.getDescription();
        academicyear = Integer.parseInt(body.getDefault_academic_year());
        //  accademic_year.setText(body.getDefault_academic_year());
       SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        preferences.edit().putString(Constants.DEFAULTACADEMIC_ID, String.valueOf(academicyear)).apply();
        academicList = new ArrayList<>();
        academicIdInList = new ArrayList<>();

        if ("200".equals(response)) {
            dismissLoadingDialog();
            List<AccademicyearModel> accademicyearModels = body.getData();
            for (int i = 0; i < accademicyearModels.size(); i++) {
                String academicYear = accademicyearModels.get(i).getAcademic_year();
                academicList.add(academicYear);
                String accademicId = accademicyearModels.get(i).getId();
                academicIdInList.add(accademicId);
            }
            setSpinnerAccademic();

        } else {
            setSpinnerAccademic();
            Snackbar.make(getView().findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }
    private void setSpinnerAccademic() {
        academicList.add(0, "-Academic Year-");
        academicIdInList.add(0, "0");
        ArrayAdapter aa = new ArrayAdapter<>(this, R.layout.spinner_item, academicList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        accademic_year.setAdapter(aa);
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        int id = Integer.parseInt(preferences.getString(Constants.DEFAULTACADEMIC_ID, "0"));
        int selectedPosition = 0;
        for (int i = 0; i < academicIdInList.size(); i++) {
            if (Integer.parseInt(academicIdInList.get(i)) == id) {
                selectedPosition = i;
            }
        }
        accademic_year.setSelection(selectedPosition);
        accademic_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accademicPosition = i;
                if (accademicPosition == 0) {
                    //recyclerView.setVisibility(View.GONE);
                } else {
                    accademicIdInListString = academicIdInList.get(accademicPosition);
                    button_saveaccademic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            preferences.edit().putString(Constants.DEFAULTACADEMIC_ID, accademicIdInListString).apply();
                            preferences.edit().putString(Constants.DEFAULTACADEMIC_NAME, academicList.get(accademicPosition)).apply();
                            finish();
                        }
                    });
                    //accademic_year.setSelection(academicyear);


                    //getStudentDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
*/
    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(getActivity());
            progress.setTitle(getString(R.string.loading_title));
            progress.setMessage(getString(R.string.loading_message));
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
    private void setStudentOptions() {
        StudentOption attendanceOption = new StudentOption();
//        0
        attendanceOption.setOptionName("Attendance");
        attendanceOption.setResourceName(R.drawable.attendance_svg);
        studentOptions.add(attendanceOption);
//        1
        StudentOption assigmentOption = new StudentOption();
        assigmentOption.setOptionName("Assignments");
        assigmentOption.setResourceName(R.drawable.assignment_svg);
        studentOptions.add(assigmentOption);
//        2
        StudentOption feesOption = new StudentOption();
        feesOption.setOptionName("Fees");
        feesOption.setResourceName(R.drawable.fees_svg);
        studentOptions.add(feesOption);

        StudentOption examOption = new StudentOption();
        examOption.setOptionName("Exam");
        examOption.setResourceName(R.drawable.exam_svg);
        studentOptions.add(examOption);

        StudentOption notificationsOption = new StudentOption();
        notificationsOption.setOptionName("Messages");
        notificationsOption.setResourceName(R.drawable.notification_svg);
        studentOptions.add(notificationsOption);

        StudentOption eventsOption = new StudentOption();
        eventsOption.setOptionName("Events");
        eventsOption.setResourceName(R.drawable.events_svg);
        studentOptions.add(eventsOption);

        StudentOption progressOption = new StudentOption();
        progressOption.setOptionName("Time Table");
        progressOption.setResourceName(R.drawable.class_routine_svg);
        studentOptions.add(progressOption);

        StudentOption donation = new StudentOption();
        donation.setOptionName("Donate Books");
        donation.setResourceName(R.drawable.donate_svg);
        studentOptions.add(donation);

        StudentOption monthlySyllabus = new StudentOption();
        monthlySyllabus.setOptionName("Monthly Syllabus");
        monthlySyllabus.setResourceName(R.drawable.monthly_syllabus);
        studentOptions.add(monthlySyllabus);

        StudentOption holidaysList = new StudentOption();
        holidaysList.setOptionName("Holidays List");
        holidaysList.setResourceName(R.drawable.holiday_svg);
        studentOptions.add(holidaysList);

        StudentOption feedbackOption = new StudentOption();
        feedbackOption.setOptionName("Feedback");
        feedbackOption.setResourceName(R.drawable.feed_back_svg);
        studentOptions.add(feedbackOption);

        //10
        StudentOption supportOption = new StudentOption();
        supportOption.setOptionName("Support");
        supportOption.setResourceName(R.drawable.support_svg);
        studentOptions.add(supportOption);

        StudentOption busTracking = new StudentOption();
        busTracking.setOptionName("Bus Tracking");
        busTracking.setResourceName(R.drawable.school_bus_blue);
        studentOptions.add(busTracking);

        StudentOption busNotRequired = new StudentOption();
        busNotRequired.setOptionName("Bus Not Required");
        busNotRequired.setResourceName(R.drawable.school_bus_red);
        studentOptions.add(busNotRequired);

        StudentOption vaccine = new StudentOption();
        vaccine.setOptionName("Vaccine");
        vaccine.setResourceName(R.drawable.vaccine_svg);
        studentOptions.add(vaccine);

        StudentOption onlineClass = new StudentOption();
        onlineClass.setOptionName("Online Classes");
        onlineClass.setResourceName(R.drawable.online_classes_svg);
        studentOptions.add(onlineClass);

        StudentOption changePassword = new StudentOption();
        changePassword.setOptionName("Change Password");
        changePassword.setResourceName(R.drawable.password_svg);
        studentOptions.add(changePassword);

        StudentOption syllabusOption = new StudentOption();
       syllabusOption.setOptionName("Logout");
       syllabusOption.setResourceName(R.drawable.logout_svg);
       studentOptions.add(syllabusOption);







//6


        //8
//        StudentOption syllabusOption = new StudentOption();
//        syllabusOption.setOptionName("Time Table");
//        syllabusOption.setResourceName(R.drawable.ic_syllabus);
//        studentOptions.add(syllabusOption);
        //9


        //11
//        StudentOption bustrackOption = new StudentOption();
//        bustrackOption.setOptionName("Bus track");
//        bustrackOption.setResourceName(R.drawable.ic_bus_tracking);
//        studentOptions.add(bustrackOption);

        //12


    }


}
