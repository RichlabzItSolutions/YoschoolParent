package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;

import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.ui.MainActivity;
import com.credoapp.parent.utils.IConstants;
import com.credoapp.parent.databinding.ItemParentStudentBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ParentStudentsAdapter extends RecyclerView.Adapter<ParentStudentsAdapter.ViewHolder> {

    private final Activity activity;
    private final List<StudentInfo> studentInfos;
    private ArrayList<String> ids = new ArrayList<>();
    private SharedPreferences preferences;
    private int money =  0;

    public ParentStudentsAdapter(Activity activity, List<StudentInfo> studentInfos) {
        this.activity = activity;
        this.studentInfos = studentInfos;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public int getAmount() {
        return money;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        preferences = activity.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_parent_student, parent, false);
        ItemParentStudentBinding binding = (ItemParentStudentBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentInfo studentInfo = studentInfos.get(position);
        holder.binding.studentName.setText(studentInfo.student_name);
        holder.binding.studentClass.setText(studentInfo.class_name);
        holder.binding.studentSchoolName.setText(studentInfo.school_name);
        holder.binding.amountPerYear.setText("Rs "+studentInfo.amount_per_student+" (P.A)");

        if ((studentInfo.payment_status.equals("0"))&&(studentInfo.parent_pay_mode.equals("1"))){
            holder.binding.trackStudent.setEnabled(false);
            holder.binding.trackStudent.setBackgroundColor(Color.parseColor("#CFE59A"));
            holder.binding.text.setVisibility(View.VISIBLE);
            holder.binding.checkBoxInFragUnChecked.setVisibility(View.VISIBLE);
            holder.binding.schoolPaid.setVisibility(View.GONE);
            holder.binding.amountPerYear.setTextColor(Color.parseColor("#000000"));
        }else if ((studentInfo.payment_status.equals("1"))&&(studentInfo.parent_pay_mode.equals("2"))){
            holder.binding.trackStudent.setEnabled(true);
            holder.binding.trackStudent.setBackgroundColor(Color.parseColor("#4FB6BE"));
            holder.binding.text.setVisibility(View.GONE);
            holder.binding.checkBoxInFragChecked.setVisibility(View.GONE);
            holder.binding.checkBoxInFragUnChecked.setVisibility(View.GONE);
            holder.binding.schoolPaid.setVisibility(View.VISIBLE);
            holder.binding.schoolPaid.setText("School Paid");
            holder.binding.schoolPaid.setVisibility(View.INVISIBLE);
            holder.binding.amountPerYear.setVisibility(View.INVISIBLE);
            holder.binding.amountPerYear.setTextColor(Color.parseColor("#B2B2B2"));
        }else {
            holder.binding.trackStudent.setEnabled(true);
            holder.binding.trackStudent.setBackgroundColor(Color.parseColor("#4FB6BE"));
            holder.binding.text.setVisibility(View.GONE);
            holder.binding.amountPerYear.setVisibility(View.INVISIBLE);
            holder.binding.checkBoxInFragChecked.setVisibility(View.GONE);
            holder.binding.checkBoxInFragUnChecked.setVisibility(View.GONE);
            holder.binding.schoolPaid.setVisibility(View.INVISIBLE);
            holder.binding.schoolPaid.setText("Paid");
            holder.binding.schoolPaid.setVisibility(View.INVISIBLE);
            holder.binding.amountPerYear.setTextColor(Color.parseColor("#B2B2B2"));
        }



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_profile_holder);
        requestOptions.error(R.drawable.ic_profile_holder);
        Glide.with(activity).setDefaultRequestOptions(requestOptions).
                load(IConstants.IMAGE_BASE_URL + "" + studentInfo.student_photo).placeholder(R.drawable.yo_one).into(holder.binding.studentImg);
    }

    @Override
    public int getItemCount() {
        return studentInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemParentStudentBinding binding;


        public ViewHolder(ItemParentStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.trackStudent.setOnClickListener(v -> {
                activity.finish();
                Intent intent = new Intent(activity, MainActivity.class);
                preferences.edit().putString(Constants.CLASS_ID, studentInfos.get(getAdapterPosition()).getClass_id()).apply();
                intent.putExtra("student_info", Parcels.wrap(studentInfos.get(getAdapterPosition())));
                activity.startActivity(intent);

            });


            binding.checkBoxInFragChecked.setOnClickListener(v -> {
                final StudentInfo enquire = ParentStudentsAdapter.this.studentInfos.get(getAdapterPosition());
                Log.d("enquire", "onClick: " + enquire);
                binding.checkBoxInFragChecked.setVisibility(View.GONE);
                binding.checkBoxInFragUnChecked.setVisibility(View.VISIBLE);
                ids.remove(enquire.getStudent_id());
                enquire.setChecked(false);
                studentInfos.set(getAdapterPosition(), enquire);
                int amount = Integer.parseInt(studentInfos.get(getAdapterPosition()).amount_per_student);
                        money = ((money-amount));
            });
            binding.checkBoxInFragUnChecked.setOnClickListener(v -> {
                binding.checkBoxInFragChecked.setVisibility(View.VISIBLE);
                binding.checkBoxInFragUnChecked.setVisibility(View.GONE);
                final StudentInfo enquire = ParentStudentsAdapter.this.studentInfos.get(getAdapterPosition());
                ids.add(enquire.getStudent_id());
                enquire.setChecked(true);
                studentInfos.set(getAdapterPosition(), enquire);

                int amountt = Integer.parseInt((studentInfos.get(getAdapterPosition()).amount_per_student));
//                            String s1 = String.valueOf(money);
//                            String s2 = String.valueOf(amountt);
//                            int n1 = Integer.parseInt(s1);
//                            int n2 = Integer.parseInt(s2);
                money = (money + amountt);

            });






//            binding.checkBoxPayment.setOnCheckedChangeListener((compoundButton, b) -> {
//                if (b) {
//                    if (ids.contains(studentInfos.get(getAdapterPosition()).getStudent_id())) {
//                        ids.remove(studentInfos.get(getAdapterPosition()).getStudent_id());
//                        int amount = Integer.parseInt(studentInfos.get(getAdapterPosition()).amount_per_student);
//                        money = ((money-amount));
//
//                    } else {
//                        ids.add(studentInfos.get(getAdapterPosition()).getStudent_id());
//                        int amountt = Integer.parseInt((studentInfos.get(getAdapterPosition()).amount_per_student));
////                            String s1 = String.valueOf(money);
////                            String s2 = String.valueOf(amountt);
////                            int n1 = Integer.parseInt(s1);
////                            int n2 = Integer.parseInt(s2);
//                        money = (money + amountt);
//                    }
//                } else {
//                    ids.remove(studentInfos.get(getAdapterPosition()).getStudent_id());
//                    int amount = Integer.parseInt(studentInfos.get(getAdapterPosition()).amount_per_student);
//                    money = (money-amount);
//                }
//            });
        }
    }
}
