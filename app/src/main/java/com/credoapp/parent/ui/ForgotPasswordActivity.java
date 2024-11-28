package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.credoapp.parent.R;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.TextView;
import com.google.gson.JsonObject;
import com.credoapp.parent.databinding.ActivityChangePasswordBinding;
import com.credoapp.parent.databinding.CustomTitleBarBinding;
import com.credoapp.parent.model.PasswordResponse;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.utils.Utils;
import java.lang.reflect.Field;
import java.util.Objects;


public class ForgotPasswordActivity extends ParentActivity {
    private ActivityChangePasswordBinding binding;
    private CustomTitleBarBinding titleBarBinding;
    private Intent intent;
    private Snackbar snack;
    boolean isFromOtp;
    private String user_mobile;
    private boolean new_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding = (ActivityChangePasswordBinding) viewDataBinding;
        intent = getIntent();
        isFromOtp = intent.getBooleanExtra("isFromOtp", false);
        user_mobile = intent.getStringExtra("user_mobile");
        new_password = intent.getBooleanExtra("new_password", false);


        if (new_password)
            setupActionBar("Reset Password");
        else
            setupActionBar("Forgot Password");
        binding.passwordEditLayout.setVisibility(View.GONE);
        binding.btnChange.setOnClickListener(v -> {
            if (!validateFields()) return;
            Utils.showProgressBar(ForgotPasswordActivity.this);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobile", user_mobile);
            jsonObject.addProperty("password", Objects.requireNonNull(binding.newPasswordText.getText()).toString());
            jsonObject.addProperty("confirm_password", binding.confirmPasswordText.getText().toString());
            StudentPresenter parentPresenter = new StudentPresenter(ForgotPasswordActivity.this);
            parentPresenter.onResetPassword(jsonObject);
        });

    }

    private boolean validateFields() {
        if (binding.newPasswordText.getText().toString().isEmpty()) {
            showSnackBar("Please Enter New Password");
            return false;
        }
        if (binding.confirmPasswordText.getText().toString().isEmpty()) {
            showSnackBar("Please Enter Confirm Password");
            return false;
        }
        if (!binding.newPasswordText.getText().toString().equals(binding.confirmPasswordText.getText().toString())) {
            showSnackBar("New Password & Confirm Password Must Match");
            return false;
        }

        return true;
    }

    boolean isUpdated = false;
    public void showSnackBar(String message) {
        if (snack == null) {
            snack = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", v -> {
                        if (isUpdated) {
                            finish();
                            startActivity(new Intent(ForgotPasswordActivity.this, LoginScreen.class));
                        }
                    });
            snack.setActionTextColor(Color.WHITE);
            try {
                // Get the Snackbar's layout
                View sbView = snack.getView();
                // Find the TextView using reflection
                Field textViewField = sbView.getClass().getDeclaredField("snackbar_text"); // Replace with actual ID if different
                textViewField.setAccessible(true);
                TextView textView = (TextView) textViewField.get(sbView);
                // Set the text color
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            snack.show();
        } else {
            snack.setText(message);
            if (!snack.isShownOrQueued())
                snack.show();
            else
                snack.show();
        }
    }


    public void onSuccessForgotPassword(PasswordResponse apiResponse) {
        Utils.hideProgressBar();
        isUpdated = true;
        if (apiResponse.getParentInfo() != null) {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginScreen.class);
            startActivity(intent);
            finish();
        } else {
            showSnackBar(apiResponse.getDescription());
        }

    }
}
