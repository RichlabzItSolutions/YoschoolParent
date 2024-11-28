package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.credoapp.parent.R;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassStatusRequest;
import com.credoapp.parent.model.onlineClassesModels.OnlineClassesResults;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.ui.OnlineClassesActivity;
import com.credoapp.parent.ui.WebViewOnlineActivity;

import org.parceler.Parcels;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineClassesAdapter extends RecyclerView.Adapter<OnlineClassesAdapter.MyViewHolder> {
    private final OnlineClassesActivity onlineClassesActivity;
    private final List<OnlineClassesResults> onlineClassesResults;
    private ProgressDialog progress;
    String studentId;

    public OnlineClassesAdapter(OnlineClassesActivity onlineClassesActivity, List<OnlineClassesResults> onlineClassesResults,String studentid) {
        this.onlineClassesActivity = onlineClassesActivity;
        this.onlineClassesResults = onlineClassesResults;
        studentId = studentid;



    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_classes_list, parent, false);



        return new OnlineClassesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OnlineClassesResults list = onlineClassesResults.get(position);
        holder.classSubjectTextOCA.setText(list.getClassName() + "-" + list.getSubjectName());
        holder.startTimeTextOCA.setText(list.getStartTime());
        holder.durationTextOCA.setText(list.getDuration() + " min");
        holder.startTimeTextOCA.setText(list.getStartTime());
        holder.topicTextOCA.setText(list.getTopic());
        switch (list.getClassStatus()) {
            case "1":
                holder.joinClassText.setVisibility(View.VISIBLE);
                holder.joinClassText.setText("Join Class");
                holder.statusOfTheClass.setText("Awaited");
                holder.statusOfTheClass.setTextColor(onlineClassesActivity.getResources().getColor(R.color.green));
                break;
            case "2":
                holder.joinClassText.setVisibility(View.VISIBLE);
                holder.joinClassText.setText("Join Class");
                holder.statusOfTheClass.setText("Going On");
                holder.statusOfTheClass.setTextColor(onlineClassesActivity.getResources().getColor(R.color.green));
                break;
            case "3":
                holder.joinClassText.setVisibility(View.GONE);
                holder.statusOfTheClass.setText("Completed");
                holder.statusOfTheClass.setTextColor(onlineClassesActivity.getResources().getColor(R.color.red));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return onlineClassesResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView joinClassText, classSubjectTextOCA, startTimeTextOCA, durationTextOCA, statusOfTheClass,
                topicTextOCA;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            joinClassText = itemView.findViewById(R.id.joinClassText);
            classSubjectTextOCA = itemView.findViewById(R.id.classSubjectTextOCA);
            startTimeTextOCA = itemView.findViewById(R.id.startTimeTextOCA);
            durationTextOCA = itemView.findViewById(R.id.durationTextOCA);
            statusOfTheClass = itemView.findViewById(R.id.statusOfTheClass);
            topicTextOCA = itemView.findViewById(R.id.topicTextOCA);

            joinClassText.setOnClickListener(view -> {
                updateStatus(view, "1", onlineClassesResults.get(getAdapterPosition()).getId(), onlineClassesResults.get(getAdapterPosition()).getStartDate(),onlineClassesResults.get(getAdapterPosition()).getJoinLink());


            });
        }
    }

    private void updateStatus(View view, String classStatus, String id, String startDate, String joinLink) {

        OnlineClassStatusRequest request = new OnlineClassStatusRequest();
        request.setClassStatus(String.valueOf(Integer.parseInt(classStatus)));
        request.setId(id);
        request.setStudentId(studentId);
        ITutorSource.getRestAPI().updateNewOnlineClassStatus(request).enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                if (response.isSuccessful()) {
                    dismissLoadingDialog();
                    Intent intent = new Intent(onlineClassesActivity, WebViewOnlineActivity.class);
                    intent.putExtra("zoomUrl",joinLink );
                    onlineClassesActivity.startActivity(intent);
                } else {
                    dismissLoadingDialog();
                    Toast.makeText(onlineClassesActivity, "try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                dismissLoadingDialog();
                t.printStackTrace();
            }
        });

    }

    private void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this.onlineClassesActivity);
            progress.setTitle(onlineClassesActivity.getString(R.string.loading_title));
            progress.setMessage(onlineClassesActivity.getString(R.string.loading_message));
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

}
