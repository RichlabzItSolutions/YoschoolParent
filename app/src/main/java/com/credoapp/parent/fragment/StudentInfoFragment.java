package com.credoapp.parent.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.credoapp.parent.model.ParentDetails;
import com.credoapp.parent.model.ParentStudentResponse;
import com.credoapp.parent.model.StudentDetails;
import com.credoapp.parent.ui.EditInfoStudentScreen;
import com.credoapp.parent.R;
import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

public class StudentInfoFragment extends Fragment implements View.OnClickListener {

    private TextView class_selection_value, gender_value, mothers_name_value,
            religion_value, nationality_value, date_of_joining_value, date_of_birth_value, address_value;
    private Button submit_button;
    private StudentDetails studentInfo;
    private ParentDetails parentInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_profile_info_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        class_selection_value = view.findViewById(R.id.class_selection_value);
        gender_value = view.findViewById(R.id.gender_value);
        mothers_name_value = view.findViewById(R.id.mothers_name_value);
        religion_value = view.findViewById(R.id.religion_value);
        nationality_value = view.findViewById(R.id.nationality_value);
        date_of_joining_value = view.findViewById(R.id.date_of_joining_value);
        date_of_birth_value = view.findViewById(R.id.date_of_birth_value);
        address_value = view.findViewById(R.id.address_value);
        submit_button = view.findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);
    }

    public void updateStudentProfile(ParentStudentResponse parentStudentResponse) {
        this.studentInfo = parentStudentResponse.getStudentInfo();
        this.parentInfo = parentStudentResponse.getParentDetails();
        class_selection_value.setText(studentInfo.getClass_name());
        gender_value.setText(studentInfo.getGender());
        mothers_name_value.setText(studentInfo.getMother_name());
        religion_value.setText(studentInfo.getReligion());
        nationality_value.setText(studentInfo.getNationality());
        date_of_joining_value.setText(studentInfo.getDate_of_join());
        date_of_birth_value.setText(studentInfo.getBirthday());
        address_value.setText(studentInfo.getStudent_address());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit_button.getId()) {
            Intent intent = new Intent(getActivity(), EditInfoStudentScreen.class);
            if (studentInfo != null) {
                intent.putExtra("student_info", Parcels.wrap(studentInfo));
                intent.putExtra("parent_info", Parcels.wrap(parentInfo));
            }
            startActivity(intent);
        }
    }
}
