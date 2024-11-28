package com.credoapp.parent.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;

public class SupportScreen extends AppCompatActivity implements View.OnClickListener {
    private View customView;
    EditText support_subject_edit, feed_back_edit;
    Spinner support_spinner;
    private Button submit_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        support_subject_edit = findViewById(R.id.support_subject_edit);
        feed_back_edit = findViewById(R.id.feed_back_edit);
        support_spinner = findViewById(R.id.support_spinner);
        String[] stringArray = getResources().getStringArray(R.array.support_array);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item,
                stringArray);
        support_spinner.setAdapter(stringArrayAdapter);
        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);
        setupActionBar("Support");
    }

    public void setupActionBar(String feesText) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_title_bar_support);
        customView = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0, 0);
        ((TextView) customView.findViewById(R.id.custom_title)).setText(feesText);
        ((TextView) customView.findViewById(R.id.custom_title)).setAllCaps(true);

        ((ImageView) customView.findViewById(R.id.back_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (submit_button.getId() == v.getId()) {
            if (support_subject_edit.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Subject",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (feed_back_edit.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Message",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                Utils.showProgressBar(this);
                ParentPresenter parentPresenter = new ParentPresenter(this);
                JsonObject jsonObject = new JsonObject();
                SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
                jsonObject.addProperty("parent_id", "" + sharedPref.getParentId());
                jsonObject.addProperty("subject", support_subject_edit.getText().toString());
                jsonObject.addProperty("message", feed_back_edit.getText().toString());
                jsonObject.addProperty("timetocall", support_spinner.getSelectedItem().toString());
                jsonObject.addProperty("admin_id", sharedPref.getAdminID());
                parentPresenter.onSendSupportRequest(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onSuccessSupportRequest(JsonObject jsonObject) {
        Utils.hideProgressBar();
        support_subject_edit.setText("");
        feed_back_edit.setText("");
        Snackbar.make(findViewById(R.id.feed_back_desc_layout),
                jsonObject.get("description").getAsString(),
                Snackbar.LENGTH_LONG).show();
    }
}
