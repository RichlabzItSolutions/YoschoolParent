package com.credoapp.parent.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;

public class FeedBackScreen extends AppCompatActivity implements View.OnClickListener {
    private View customView;
    EditText feed_back_edit;
    Button submit_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setupActionBar("Feed Back");
        feed_back_edit = findViewById(R.id.feed_back_edit);
        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);
    }


    public void setupActionBar(String feesText) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_title_bar_feedback);
        customView = getSupportActionBar().getCustomView();
//        ViewDataBinding viewDataBinding = DataBindingUtil.bind(customView);
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
            if (feed_back_edit.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your feedback",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                Utils.showProgressBar(this);
                ParentPresenter parentPresenter = new ParentPresenter(this);
                JsonObject jsonObject = new JsonObject();
                SharedPref sharedPref = SharedPref.getmSharedPrefInstance(this);
                jsonObject.addProperty("parent_id", "" + sharedPref.getParentId());
                jsonObject.addProperty("description", feed_back_edit.getText().toString());
                jsonObject.addProperty("admin_id", sharedPref.getAdminID());
                parentPresenter.onSendFeedback(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onSuccessFeedback(JsonObject jsonObject) {
        Utils.hideProgressBar();
        feed_back_edit.setText("");
        Snackbar.make(findViewById(R.id.feed_back_desc_layout),
                jsonObject.get("description").getAsString(),
                Snackbar.LENGTH_LONG).show();

    }
}
