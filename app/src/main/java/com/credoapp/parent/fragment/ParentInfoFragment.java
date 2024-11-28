package com.credoapp.parent.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.ParentDetails;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.model.StudentDetails;
import com.credoapp.parent.model.UpdateParentModel.UpdateParentRequest;
import com.credoapp.parent.model.otpModelsForNumber.OtpRequest;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;
import com.credoapp.parent.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentInfoFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ParentInfoFragment.class.getSimpleName();
    private TextView name_selection_value, mobile_number_value;
    private Button submit_button;
    private LinearLayout parentMobileNoLayout, parentNameLayout;
    private ParentDetails parentDetails;
    private EditText parentName, parentMobile;
    private Button updateButton;
    private ParentDetails studentDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.parent_profile_info_layout, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name_selection_value = view.findViewById(R.id.name_selection_value);
        mobile_number_value = view.findViewById(R.id.mobile_number_value);
        submit_button = view.findViewById(R.id.submit_button);
        parentMobileNoLayout = view.findViewById(R.id.parentMobileNoLayout);
        parentNameLayout = view.findViewById(R.id.parentNameLayout);
        parentMobile = view.findViewById(R.id.parentMobile);
        parentName = view.findViewById(R.id.parentName);
        updateButton = view.findViewById(R.id.updateButton);
        submit_button.setOnClickListener(this);
        updateButton.setOnClickListener(v -> {

            if (parentName.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Enter parent name", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e(TAG, "onViewCreated: " + parentMobile.getText().toString());

            if (!(parentMobile.getText().toString().matches(Constants.MobilePattern))) {
                parentMobile.setError("Enter valid mobile number");
                Toast.makeText(getActivity(), "Enter valid parent mobile no.", Toast.LENGTH_SHORT).show();
            } else {
                updateParentInfo();
            }


        });
    }

    private void updateParentInfo() {
        Utils.showProgressBar(getActivity());
        UpdateParentRequest request = new UpdateParentRequest();
        request.setParentId(parentDetails.parent_id);
        request.setParentMobile(parentMobile.getText().toString());
        request.setParentName(parentName.getText().toString());
        ITutorSource.getRestAPI().updateParentInfo(request).enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                Utils.hideProgressBar();
                if (response.isSuccessful()) {
                    Log.d("", "onResponse  : " + response);
                    updateParentInfoResponse(Objects.requireNonNull(response.body()));

                } else {
                    Toast.makeText(getActivity(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Utils.hideProgressBar();
//                if (t instanceof SocketTimeoutException) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }
        });


    }

    private void updateParentInfoResponse(GlobalResponse body) {
        if (body.getResponseCode().equals("200")) {

            shopPopUp(parentMobile.getText().toString());

            parentMobileNoLayout.setVisibility(View.GONE);
            parentNameLayout.setVisibility(View.GONE);
            submit_button.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
            mobile_number_value.setVisibility(View.VISIBLE);
            name_selection_value.setVisibility(View.VISIBLE);
            name_selection_value.setText(parentName.getText().toString());
            mobile_number_value.setText(parentMobile.getText().toString());
//            Intent in = new Intent(getActivity(), MainActivity.class);
//            startActivity(in);
//            Intent intent = new Intent(getContext(), OTPScreen.class);
//            startActivity(intent);
//            Objects.requireNonNull(getActivity()).finish();


            Toast.makeText(getActivity(), body.getDescription(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), body.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void shopPopUp(String mobileNo) {
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//
//        builder.setTitle("Alert !!");
//        builder.setMessage(description);
//
//        builder.setPositiveButton("OK", (dialog, which) ->{
//            dialog.dismiss();
//            Intent in = new Intent(getActivity(), MainActivity.class);
//            startActivity(in);
//        });
//        android.app.AlertDialog alert = builder.create();
//        alert.setCancelable(false);
//        alert.show();


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        @SuppressLint("InflateParams") View mView = LayoutInflater.from(getActivity()).inflate(R.layout.forgot_layout_otp, null);
        Button verifyButtonChangeNo = mView.findViewById(R.id.verifyButtonChangeNo);
        TextView otpEditText = mView.findViewById(R.id.otpEditText);
        TextView textViewOtpToSentNo = mView.findViewById(R.id.textViewOtpToSentNo);

        String textNumber = "<font color=#00000>Otp had send to </font> <font color=#4FB6BE>" + mobileNo + "</font>";
        textViewOtpToSentNo.setText(Html.fromHtml(textNumber));
//        Pinview pinView = mView.findViewById(R.id.pinView);
//        pinView.setOnTouchListener((v, event) -> {
//            v.setFocusable(true);
//            v.setFocusableInTouchMode(true);
//            return false;
//        });
//        String text = "<font color=#00000>Didn't Receive OTP? </font> <font color=#E5373F>CLick Here</font>";
//        didNotReceiveOtpText.setText(Html.fromHtml(text));

        builder.setView(mView);
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(true);

//        didNotReceiveOtpText.setOnClickListener(v -> {
//            ResendOtpRequest request = new ResendOtpRequest();
//            request.setMobile(mobileNo);
//
//            if (Constants.haveInternet(getApplicationContext())) {
//                //Utils.showProgressBar(getApplicationContext());
//                showLoadingDialog();
//                TrainersSource.getRestAPI().resendOtp(request).enqueue(new Callback<ResendOtpResponse>() {
//                    @Override
//                    public void onResponse(@NonNull Call<ResendOtpResponse> call, @NonNull Response<ResendOtpResponse> response) {
//                        Log.d(TAG, "onResponse : " + response);
//                        dismissLoadingDialog();
//                        if (response.isSuccessful()) {
//                            resendOtpResponse(Objects.requireNonNull(response.body()));
//                        } else {
//                            Toast.makeText(LogInScreen.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<ResendOtpResponse> call, @NonNull Throwable t) {
//                        t.printStackTrace();
//                        //if (t instanceof SocketTimeoutException) {
//                        Toast.makeText(LogInScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_SHORT).show();
////                        }
//                        //Toast.makeText(LogInScreen.this, "Server internal error try again", Toast.LENGTH_SHORT).show();
//                        dismissLoadingDialog();
//                    }
//                });
//            } else {
//                Constants.InternetSettings(LogInScreen.this);
//            }
//        });
        verifyButtonChangeNo.setOnClickListener(v -> {

            if (otpEditText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Enter Otp", Toast.LENGTH_SHORT).show();
                return;
            }
            if (otpEditText.getText().toString().length() == 4) {
                OtpRequest request = new OtpRequest();
                request.setChangeMobileTo(mobileNo);
                request.setOtp(otpEditText.getText().toString());
                Utils.showProgressBar(getActivity());
                    ITutorSource.getRestAPI().verifyOtpNumberChange(request).enqueue(new Callback<GlobalResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                            Log.d(TAG, "onResponse : " + response);
                            Utils.hideProgressBar();
                            if (response.isSuccessful()) {
                                verifyOtpNumberChangeResponse(Objects.requireNonNull(response.body()), dialog);
                            } else {
                                Toast.makeText(getActivity(), R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
//                            if (t instanceof SocketTimeoutException) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                //Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_SHORT).show();
//                            }
                            //Toast.makeText(LogInScreen.this, "Server internal error try again", Toast.LENGTH_SHORT).show();
                            Utils.hideProgressBar();
                        }
                    });


            } else {
                Toast.makeText(getActivity(), "Enter Otp", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void verifyOtpNumberChangeResponse(GlobalResponse body, AlertDialog dialog) {

        if (body.getResponseCode().equals("200")){
            dialog.dismiss();
            Toast.makeText(getActivity(), body.getDescription(), Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        }else {
            Toast.makeText(getActivity(), body.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    private StudentDetails studentInfo;

    public void updateParentProfile(ParentStudentResponse parentStudentResponse) {
        this.parentDetails = parentStudentResponse.getParentDetails();
        studentInfo = parentStudentResponse.getStudentInfo();
        name_selection_value.setText(parentDetails.getParent_name());
        mobile_number_value.setText(parentDetails.getParent_mobile());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit_button.getId()) {

            parentMobileNoLayout.setVisibility(View.VISIBLE);
            parentNameLayout.setVisibility(View.VISIBLE);
            mobile_number_value.setVisibility(View.GONE);
            submit_button.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
            name_selection_value.setVisibility(View.GONE);
            parentName.setText(name_selection_value.getText().toString());
            parentMobile.setText(mobile_number_value.getText().toString());


//            Intent intent = new Intent(getActivity(), EditInfoStudentScreen.class);
//            if (studentInfo != null) {
//                intent.putExtra("student_info", Parcels.wrap(studentInfo));
//                intent.putExtra("parent_info", Parcels.wrap(parentDetails));
//            }
//            startActivity(intent);
        }
    }


}
