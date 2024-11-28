package com.credoapp.parent.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.credoapp.parent.R;
import com.credoapp.parent.model.Assignment;
import com.credoapp.parent.ui.AddAssignmentActivity;
import com.credoapp.parent.ui.AssignementsScreen;
import com.credoapp.parent.ui.FullImageViewScreen;
import com.credoapp.parent.ui.ImageFullView;
import com.credoapp.parent.ui.ViewRepliedAssignments;
import com.squareup.picasso.Picasso;
import com.credoapp.parent.databinding.ItemAssignmentBinding;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder> {
    private final AssignementsScreen assignementsScreen;
    private final List<Assignment> assignments;
    private ArrayList<String> imagesList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(assignementsScreen),
                R.layout.item_assignment, parent, false);
        ItemAssignmentBinding binding = (ItemAssignmentBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);


        if (assignment.getTitle().equals("")){
            if (assignment.getAssignmentImage().equals("")){
                String des = assignment.getTitle();
                String s = des.replaceAll("<br/>", "\n");
                holder.binding.assignmentTitle.setText(s);
                holder.binding.assignmentTitle.setVisibility(View.VISIBLE);
                holder.binding.imageViewAssignmentList.setVisibility(View.GONE);
            }else {
                holder.binding.assignmentTitle.setVisibility(View.GONE);
                holder.binding.imageViewAssignmentList.setVisibility(View.VISIBLE);
                Picasso.with(assignementsScreen).load(assignment.getAssignmentImage()).placeholder(R.drawable.place_holder)
                        .into(holder.binding.imageViewAssignmentList);
            }

        }else {
            String des = assignment.getTitle();
            String s = des.replaceAll("<br/>", "\n");
            holder.binding.assignmentTitle.setText(s);
            holder.binding.assignmentTitle.setVisibility(View.VISIBLE);
            holder.binding.imageViewAssignmentList.setVisibility(View.GONE);
        }


//        holder.binding.assignmentText.setText("Assignment Type: " + assignment.getAssignment_type());
//        holder.binding.subjectText.setText("Subject: " + assignment.getSubject_name());
//        holder.binding.lastDateText.setText("Last Date: " + assignment.getTo_date());
//        holder.binding.submittedText.setText("Submitted date: " + assignment.getTo_date());

        holder.binding.fromDateText.setText(assignment.getFrom_date());
        holder.binding.toDateText.setText(assignment.getTo_date());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemAssignmentBinding binding;

        public ViewHolder(ItemAssignmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.replyAssignmentText.setOnClickListener(view -> {
                Intent in = new Intent(assignementsScreen, ViewRepliedAssignments.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("cameFrom","assignments");
                in.putExtra("viewSubmit","submit");
                in.putExtra("assignmentId", assignments.get(getAdapterPosition()).getAssignmentId());
                assignementsScreen.startActivity(in);
            });
            binding.submitAssignmentText.setOnClickListener(view -> {
                Intent in = new Intent(assignementsScreen, AddAssignmentActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("cameFrom","assignments");
                in.putExtra("viewSubmit","submit");
                in.putExtra("assignmentId", assignments.get(getAdapterPosition()).getAssignmentId());
                assignementsScreen.startActivity(in);
            });
            binding.viewAssignmentText.setOnClickListener(view -> {
                Intent in = new Intent(assignementsScreen, AddAssignmentActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("cameFrom","assignments");
                in.putExtra("viewSubmit","view");
                in.putExtra("assignmentId", assignments.get(getAdapterPosition()).getAssignmentId());
                assignementsScreen.startActivity(in);
            });

            binding.imageViewAssignmentList.setOnClickListener(v -> {
//                    showFullImageInDialog(assignments.get(getAdapterPosition()).assignmentImage,v);
                Intent in = new Intent(assignementsScreen, FullImageViewScreen.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("cameFrom","assignments");
                in.putExtra("imageUrl", assignments.get(getAdapterPosition()).assignmentImage);
                assignementsScreen.startActivity(in);
            });
        }
    }

    public AssignmentsAdapter(AssignementsScreen assignementsScreen, List<Assignment> assignments) {
        this.assignementsScreen = assignementsScreen;
        this.assignments = assignments;
    }

    private static String removeWords(String word, String remove) {
        return word.replace(remove,"");
    }


    private void showFullImageInDialog(String imageUrl, View view) {

//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());


        @SuppressLint("InflateParams") View myView = LayoutInflater.from(assignementsScreen).inflate(R.layout.image_dialog_layout, null);
//        LayoutInflater inflater = LayoutInflater.from(context);
////                getLayoutInflater();
//        View dialoglayout = inflater.inflate(R.layout.image_dialog_layout, null);
        ImageView imgOriginal = myView.findViewById(R.id.imgOriginal);


        if ((imageUrl==null)||(imageUrl.equals(""))){
            imgOriginal.setImageResource(R.drawable.download_p);
        }else {
            Glide.with(assignementsScreen).load(imageUrl)
                    .placeholder(R.drawable.download_p)
                    .thumbnail(0.5f)
                    .into(imgOriginal);
        }


//         .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)

        builder.setView(myView);
        builder.create();
        builder.show();
    }
}
