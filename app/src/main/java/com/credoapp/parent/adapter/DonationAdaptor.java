package com.credoapp.parent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.model.donateListModels.DeleteRequest;
import com.credoapp.parent.model.donateListModels.DeleteResponse;
import com.credoapp.parent.model.donateListModels.DonateCallRequest;
import com.credoapp.parent.model.donateListModels.DonateCallResponse;
import com.credoapp.parent.model.donateListModels.MyDonationsList;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.ui.DonateBooksActivity;
import com.credoapp.parent.ui.DonateBooksList;
import com.credoapp.parent.R;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonationAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE = 0;
    private static final int MINE = 1;
    private static final int OTHER = 2;
    private static final int EMPTY = 3;
    private final Context applicationContext;
    private String studentId;
    private SharedPref sharedPref;


    private final List<MyDonationsList> donations;

    public DonationAdaptor(DonateBooksList applicationContext, List<MyDonationsList> donations) {
        this.applicationContext = applicationContext;
        this.donations = donations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_title, parent, false);
                return new TitleViewHolder(view);
            case MINE:
            case OTHER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_adaptor_my_list, parent, false);
                return new MyDonationsViewHolder(view);
            case EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_donations, parent, false);
                return new EmptyViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (donations.get(position).getTitle()) {
            case "my":
                return MINE;
            case "other":
                return OTHER;
            case "empty":
                return EMPTY;
        }
        return TITLE;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int itemViewType = getItemViewType(position);
        final MyDonationsList list = donations.get(position);

        SharedPreferences sp = applicationContext.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        studentId = sp.getString(Constants.PARENT_ID, studentId);

        switch (itemViewType) {
            case EMPTY:
                Log.d("", "onBindViewHolder: came to empty");
                ((EmptyViewHolder) holder).tvNoDonationsTitle.setText(list.getEmptyTitle());
                break;
            case TITLE:
                ((TitleViewHolder) holder).tvGroupTitle.setText(list.getTitle());
                break;
            case MINE:
                ((MyDonationsViewHolder) holder).editImageViewMine.setVisibility(View.VISIBLE);
                ((MyDonationsViewHolder) holder).deleteImageViewMine.setVisibility(View.VISIBLE);
                ((MyDonationsViewHolder) holder).nameTestDonateAdaptorMine.setText(list.getStudent_name());
                ((MyDonationsViewHolder) holder).syllabusDonateAdaptorMine.setText(list.getSyllabus());
                ((MyDonationsViewHolder) holder).classDonateAdaptorMine.setText(list.getClasses());
                ((MyDonationsViewHolder) holder).descriptionDonateAdaptorMine.setText(list.getDescription());
                String amount = list.getAmount();
                String donationMode = list.getDonation_mode();
                String photo = list.getPhoto();

                if ((photo == null) || (photo.equals(""))) {
                    //((MyDonationsViewHolder) holder).bookImageView.setBackgroundResource(R.drawable.profile_svg);
                }
                Glide.with(applicationContext).load("http://credoapp.in/uploads/books/" + photo)
                        .thumbnail(0.5f)
//                        .crossFade()
                        .centerCrop()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((MyDonationsViewHolder) holder).bookImageView);

                if (donationMode.equals("1")) {
                    //((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText(R.string.free);
                    ((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText("₹" + amount);
                } else {

                    ((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText("₹" + amount);
                }
                ((MyDonationsViewHolder) holder).deleteImageViewMine.setOnClickListener(view -> {
                    String donationId = list.getDonation_id();
                    showAlertDialog(view, donationId);
                });

                ((MyDonationsViewHolder) holder).editImageViewMine.setOnClickListener(view -> {
                    String donationId = list.getDonation_id();
                    Log.d("donationId", donationId + "");
                    Intent in = new Intent(applicationContext, DonateBooksActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("donationId", donationId);
                    in.putExtra("value", "updateDonateBooks");
                    applicationContext.startActivity(in);
                });
                break;
            case OTHER:
                ((MyDonationsViewHolder) holder).callImageViewMine.setVisibility(View.VISIBLE);
                ((MyDonationsViewHolder) holder).nameTestDonateAdaptorMine.setText(list.getStudent_name());
                ((MyDonationsViewHolder) holder).syllabusDonateAdaptorMine.setText(list.getSyllabus());
                ((MyDonationsViewHolder) holder).classDonateAdaptorMine.setText(list.getClasses());
                ((MyDonationsViewHolder) holder).descriptionDonateAdaptorMine.setText(list.getDescription());

                String donationModeOther = list.getDonation_mode();
                String amountOther = list.getAmount();
                final String donation = list.getDonation_id();
                final String phoneNo = list.getNumber();


                String photoT = list.getPhoto();

                if ((photoT == null) || (photoT.equals(""))) {
                    //((MyDonationsViewHolder) holder).bookImageView.setBackgroundResource(R.drawable.profile_svg);
                }
                Glide.with(applicationContext).load("http://credoapp.in/uploads/books/" + photoT)
                        .thumbnail(0.5f)
//                        .crossFade()
                        .centerCrop()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((MyDonationsViewHolder) holder).bookImageView);

                if (donationModeOther.equals("1")) {
                    //((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText(R.string.free);
                    ((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText("₹" + amountOther);
                } else {

                    ((MyDonationsViewHolder) holder).costDonateAdaptorMine.setText("₹" + amountOther);
                }


                if ((phoneNo == null) || (phoneNo.equals("") || (phoneNo.equals("null")))) {
                    ((MyDonationsViewHolder) holder).callImageViewMine.setVisibility(View.GONE);
                }


                ((MyDonationsViewHolder) holder).callImageViewMine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        int result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE);
//                        if (result == PackageManager.PERMISSION_DENIED){
//                            Toast.makeText(applicationContext, "You need to accept call permission to do this task", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent();
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.fromParts("package", applicationContext.getPackageName(), null);
//                            intent.setData(uri);
//                            applicationContext.startActivity(intent);
//                        }else {


                        callRequest(donation, phoneNo);

//                        }
                    }
                });
                break;
        }
    }

    private void callRequest(String donation, final String phoneNo) {
        DonateCallRequest request = new DonateCallRequest();
        request.setDonationId(donation);
        request.setStudentId(studentId);

        ITutorSource.getRestAPI().callDonation(request).enqueue(new Callback<DonateCallResponse>() {
            @Override
            public void onResponse(@NonNull Call<DonateCallResponse> call, @NonNull Response<DonateCallResponse> response) {
                Log.d("", "onResponse : login " + response);
                callDonationResponse(Objects.requireNonNull(response.body()), phoneNo);
            }

            @Override
            public void onFailure(@NonNull Call<DonateCallResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
//                                    Snackbar.make(findViewById(android.R.id.content),"internal error try again",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void callDonationResponse(DonateCallResponse body, String phoneNo) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        switch (response) {
            case "200":

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNo));
                applicationContext.startActivity(intent);
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                callIntent.setData(Uri.parse("tel:" + phoneNo));
//                if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                applicationContext.startActivity(callIntent);
                break;
            case "204":
                Toast.makeText(applicationContext, description, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(applicationContext, R.string.unexpectedError, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showAlertDialog(View view, final String donationId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this donation ?");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteDonation(donationId);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteDonation(String donationId) {

        Log.d("donationId", donationId + "");
        DeleteRequest request = new DeleteRequest();
        request.setDonationId(donationId);
        ITutorSource.getRestAPI().deleteDonation(request).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteResponse> call, @NonNull Response<DeleteResponse> response) {
                Log.d("", "onResponse  " + response);
                deleteDonationResponse(Objects.requireNonNull(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<DeleteResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(applicationContext, "internal error try again", Toast.LENGTH_SHORT).show();
//                                   Snackbar.make(findViewById(android.R.id.content),"internal error try again",Snackbar.LENGTH_SHORT).show();
                }
                Toast.makeText(applicationContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDonationResponse(DeleteResponse body) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        if ("200".equals(response)) {
            if (applicationContext instanceof DonateBooksList) {
                ((DonateBooksList) applicationContext).getDonationList();
            }
        } else {
            Toast.makeText(applicationContext, R.string.unexpectedError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupTitle;

        TitleViewHolder(View itemView) {
            super(itemView);
            tvGroupTitle = itemView.findViewById(R.id.tvGroupTitle);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoDonationsTitle;

        EmptyViewHolder(View itemView) {
            super(itemView);
            tvNoDonationsTitle = itemView.findViewById(R.id.tvNoDonationsTitle);
        }
    }

    public class MyDonationsViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTestDonateAdaptorMine, syllabusDonateAdaptorMine, classDonateAdaptorMine,
                descriptionDonateAdaptorMine, costDonateAdaptorMine;
        final ImageView editImageViewMine, callImageViewMine, deleteImageViewMine, bookImageView;


        MyDonationsViewHolder(View itemView) {
            super(itemView);
            nameTestDonateAdaptorMine = itemView.findViewById(R.id.nameTestDonateAdaptorMine);
            syllabusDonateAdaptorMine = itemView.findViewById(R.id.syllabusDonateAdaptorMine);
            classDonateAdaptorMine = itemView.findViewById(R.id.classDonateAdaptorMine);
            descriptionDonateAdaptorMine = itemView.findViewById(R.id.descriptionDonateAdaptorMine);
            costDonateAdaptorMine = itemView.findViewById(R.id.costDonateAdaptorMine);

            editImageViewMine = itemView.findViewById(R.id.editImageViewMine);
            callImageViewMine = itemView.findViewById(R.id.callImageViewMine);
            deleteImageViewMine = itemView.findViewById(R.id.deleteImageViewMine);

            bookImageView = itemView.findViewById(R.id.bookImageView);
        }
    }
}
