package com.credoapp.parent.ui;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.model.APIResponse;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ActivityNotregisteredParentBinding;

public class NoListedSchoolScreen extends AppCompatActivity {

    ActivityNotregisteredParentBinding binding;
    private Snackbar snack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_notregistered_parent);
        binding = (ActivityNotregisteredParentBinding) viewDataBinding;
        String user_mobile = getIntent().getStringExtra("user_mobile");
        binding.username.setText(user_mobile);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) return;
                Utils.showProgressBar(NoListedSchoolScreen.this);
                ParentPresenter parentPresenter = new ParentPresenter(NoListedSchoolScreen.this);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", binding.parentname.getText().toString());
                jsonObject.addProperty("mobile", binding.username.getText().toString());
                jsonObject.addProperty("school_name", binding.schoolNameText.getText().toString());
                jsonObject.addProperty("city", binding.cityText.getText().toString());
                parentPresenter.onRequestNotListedSchool(jsonObject);
            }
        });

    }

    private void errorValidityMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private boolean validateFields() {
        if (binding.username.getText().toString().isEmpty()) {
            errorValidityMessage("Please Enter Mobile Number.");
            return false;
        }

        if (binding.username.getText().toString().length() < 10) {
            errorValidityMessage("Please Enter Valid Mobile Number.");
            return false;
        }
        if (binding.parentname.getText().toString().length() < 3) {
            errorValidityMessage("Please Enter Full Name");
            return false;
        }
        if (binding.schoolNameText.getText().toString().isEmpty()) {
            errorValidityMessage("Please Enter School Name.");
            return false;
        }
        if (binding.cityText.getText().toString().isEmpty()) {
            errorValidityMessage("Please Enter City.");
            return false;
        }
        if (binding.cityText.getText().toString().length() < 3) {
            errorValidityMessage("Please Enter Full City Name");
            return false;
        }
        return true;
    }

    public void onSuccessNotListedSchool(APIResponse apiResponse) {
        Utils.hideProgressBar();
//        showSnackBar(apiResponse.getDescription());
        Toast.makeText(this, apiResponse.getDescription(), Toast.LENGTH_LONG).show();
        finish();
    }

    public void showSnackBar(String message) {
        if (snack == null) {
            snack = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(new Intent(NoListedSchoolScreen.this, LoginScreen.class));

                        }
                    });
            snack.setActionTextColor(Color.WHITE);
            View sbView = snack.getView();
//            TextView textView = sbView.findViewById(R.id.snackbar_text);
            //textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            snack.show();
        } else {
            snack.setText(message);
            if (!snack.isShownOrQueued())
                snack.show();
            else
                snack.show();
        }
    }
}
