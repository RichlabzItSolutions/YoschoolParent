package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.google.gson.JsonObject;
import com.credoapp.parent.databinding.ActivityChangePasswordBinding;
import com.credoapp.parent.databinding.CustomTitleBarBinding;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;

import java.lang.reflect.Field;


public class ChangePasswordActivity extends ParentActivity {

    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    private SharedPref sharedPref;
    private ActivityChangePasswordBinding binding;
    private CustomTitleBarBinding titleBarBinding;
    private Intent intent;
    private Snackbar snack;
    String title_screen;
    Button btnChange;
    boolean isFromOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding = (ActivityChangePasswordBinding) viewDataBinding;
        intent = getIntent();
        sharedPref = SharedPref.getmSharedPrefInstance(this);
        isFromOtp = intent.getBooleanExtra("isFromOtp", false);
        if (isFromOtp)
            setupActionBar("Forgot Password");
        else
            setupActionBar("Change Password");

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) return;
                Utils.showProgressBar(ChangePasswordActivity.this);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("parent_id", sharedPref.getParentId());
                jsonObject.addProperty("old_password", binding.oldPasswordText.getText().toString());
                jsonObject.addProperty("new_password", binding.newPasswordText.getText().toString());
                jsonObject.addProperty("confirm_password", binding.confirmPasswordText.getText().toString());
                ParentPresenter parentPresenter = new ParentPresenter(ChangePasswordActivity.this);
                parentPresenter.onChangePassword(jsonObject);
            }
        });

    }

    private boolean validateFields() {
        if (binding.oldPasswordText.getText().toString().isEmpty()) {
            showSnackBar("Please Enter Old Password");
            return false;
        }
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

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
        if (snack == null) {
            snack = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", v -> {
                        if (isUpdated)
                            finish();
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
        } else {
            snack.setText(message);
            if (!snack.isShownOrQueued())
                snack.show();
            else
                snack.show();
        }
    }
    public void onSuccessChangePassword(JsonObject jsonObject) {
        Utils.hideProgressBar();
        isUpdated = true;
        showSnackBar(jsonObject.get("description").getAsString());
    }
}
