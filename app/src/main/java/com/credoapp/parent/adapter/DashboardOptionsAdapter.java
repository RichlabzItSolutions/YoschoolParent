package com.credoapp.parent.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.credoapp.parent.common.Constants;
import com.credoapp.parent.events.EventsActivity;
import com.credoapp.parent.model.GlobalResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.StudentOption;
import com.credoapp.parent.model.busNotRequiredModels.BusNOtRequiredRequest;
import com.credoapp.parent.model.driverDetails.DriverRequest;
import com.credoapp.parent.preference.SharedPref;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.ui.AssignementsScreen;
import com.credoapp.parent.ui.AttendanceScreen;
import com.credoapp.parent.ui.BusTracking;
import com.credoapp.parent.ui.ChangePasswordActivity;
import com.credoapp.parent.ui.ClassRoutine;
import com.credoapp.parent.ui.DonateBooksList;
import com.credoapp.parent.ui.ExamsActivity;
import com.credoapp.parent.ui.FeedBackScreen;
import com.credoapp.parent.ui.FeesDetailsScreen;
import com.credoapp.parent.ui.HolidaysListScreen;
import com.credoapp.parent.ui.LoginScreen;
import com.credoapp.parent.ui.NotificationsScreen;
import com.credoapp.parent.ui.OnlineClassesActivity;
import com.credoapp.parent.ui.PdfActivity;
import com.credoapp.parent.ui.SupportScreen;
import com.credoapp.parent.ui.VaccineScreen;
import com.credoapp.parent.R;
import com.credoapp.parent.databinding.ItemStudentOptionsBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardOptionsAdapter extends RecyclerView.Adapter<DashboardOptionsAdapter.ViewHolder> {

    private final Activity activity;
    private final ArrayList<StudentOption> studentOptions;
    private StudentInfo studentInfo;
    private ProgressDialog progress;
    private SharedPref sharedPref;


    public DashboardOptionsAdapter(Activity activity,
                                   ArrayList<StudentOption> studentOptions,
                                   StudentInfo studentInfo) {
        sharedPref = SharedPref.getmSharedPrefInstance(activity);

        this.activity = activity;
        this.studentOptions = studentOptions;
        this.studentInfo = studentInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_student_options, parent, false);
        ItemStudentOptionsBinding binding = (ItemStudentOptionsBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentOption studentOption = studentOptions.get(position);
        holder.binding.optionsName.setText(studentOption.getOptionName());
        holder.binding.optionsName.setAllCaps(true);
        holder.binding.imgOption.setImageResource(studentOption.getResourceName());
    }

    @Override
    public int getItemCount() {
        return studentOptions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemStudentOptionsBinding binding;


        public ViewHolder(ItemStudentOptionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (getAdapterPosition() == 1) {
                    Intent intent = new Intent(activity, AssignementsScreen.class);
                    intent.putExtra("cameFrom", "dashBoard");
                    intent.putExtra("student_id", studentInfo.getStudent_id());
                    intent.putExtra("classId", studentInfo.getClass_id());
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 0) {
                    Intent intent = new Intent(activity, AttendanceScreen.class);
                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    activity.startActivity(intent);
                }
//                else if (getAdapterPosition() == 3) {
//                    Intent intent = new Intent(activity, MessagesScreen.class);
//                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
//                    activity.startActivity(intent);
//                }
                else if (getAdapterPosition() == 2) {
                    Intent intent = new Intent(activity, FeesDetailsScreen.class);
                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 3) {
                    //Toast.makeText(activity, "coming soon", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(activity, ExamTimeTableResultsActivity.class);
//                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    Intent intent = new Intent(activity, ExamsActivity.class);
                    intent.putExtra("student_id", studentInfo.getStudent_id());
                    intent.putExtra("classId", studentInfo.getClass_id());
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 4) {
                    Intent intent = new Intent(activity, NotificationsScreen.class);
                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 10) {
                    Intent intent = new Intent(activity, FeedBackScreen.class);
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 11) {
                    Intent intent = new Intent(activity, SupportScreen.class);
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 12) {
                    int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(activity, "You need to accept LOCATION permission", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivity(intent);
                        }, 2000);
                    } else {
                        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                        boolean gps_enabled = false;
                        try {
                            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (gps_enabled) {
                            getDriverDetails(v);
                        } else {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", (dialog, id) -> activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                            final AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                } else if (getAdapterPosition() == 5) {
                    Intent intent = new Intent(activity, EventsActivity.class);
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 6) {
                    Intent intent = new Intent(activity, ClassRoutine.class);
                    intent.putExtra("student_id", studentInfo.getStudent_id());
                    intent.putExtra("classId", studentInfo.getClass_id());
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 7) {
                    Intent intent = new Intent(activity, DonateBooksList.class);
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 8) {
                    Intent intent = new Intent(activity, PdfActivity.class);
                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 9) {
                    Intent intent = new Intent(activity, HolidaysListScreen.class);
                    intent.putExtra("student_info", Parcels.wrap(studentInfo));
                    activity.startActivity(intent);
                } else if (getAdapterPosition() == 13) {
//                    Intent intent = new Intent(activity, VaccineScreen.class);
//                    activity.startActivity(intent);
                    sendStatusToServer();
                    Toast.makeText(activity, "Bus not required....", Toast.LENGTH_SHORT).show();
                }else if (getAdapterPosition() == 14) {
                    Intent intent = new Intent(activity, VaccineScreen.class);
                    activity.startActivity(intent);
                }else if (getAdapterPosition() == 15) {
                    Intent intent = new Intent(activity, OnlineClassesActivity.class);
                    activity.startActivity(intent);
                }else if (getAdapterPosition() == 16) {
                    Intent intent = new Intent(activity, ChangePasswordActivity.class);
                    activity.startActivity(intent);
                }else if (getAdapterPosition() == 17) {
                    sharedPref.saveParentId("");
                    SharedPreferences preferences = activity.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(activity, LoginScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
        }
    }

    private void sendStatusToServer() {
        BusNOtRequiredRequest request = new BusNOtRequiredRequest();
        request.setStudentId(studentInfo.student_id);
        ITutorSource.getRestAPI().setBusNotRequired(request).enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                dismissLoadingDialog();
                if (response.isSuccessful()) {
                    setBusNotRequiredResponse(Objects.requireNonNull(response.body()));
                } else {
                    Toast.makeText(activity, R.string.unexpected_error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                dismissLoadingDialog();
                t.printStackTrace();
            }
        });

    }

    private void setBusNotRequiredResponse(GlobalResponse body) {
        Toast.makeText(activity, body.getDescription(), Toast.LENGTH_SHORT).show();
    }

    private void getDriverDetails(View view) {
        showLoadingDialog(view);

        DriverRequest request = new DriverRequest();
        request.setStudentId(studentInfo.getStudent_id());
        ITutorSource.getRestAPI().getRoute(request).enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                dismissLoadingDialog();
                if (response.isSuccessful()) {
                    getRouteResponse(Objects.requireNonNull(response.body()), view);
                } else {
                    Toast.makeText(activity, R.string.unexpected_error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                dismissLoadingDialog();
                t.printStackTrace();
            }
        });


    }

    private void getRouteResponse(GlobalResponse body, View view) {
        String response = body.getResponseCode();
        //String description = body.getDescription();

        if (response.equals("200")) {


            if ((body.getTripStatus().equals("2") || (body.getDropStatus().equals("2")))) {
                Intent intent = new Intent(activity, BusTracking.class);
                activity.startActivity(intent);
            } else {
                showPopUp(view);
            }


//            if ((body.getTripStatus().equals("4") || (body.getDropStatus().equals("4")))){
//                showPopUp(view);
//
//            }else {
//                if (body.getTripStatus().equals("0")) {
//                    showPopUp(view);
//                }
//            }
//


//            if (body.getTripStatus().equals("0")) {
//                showPopUp(view);
//            } else if ((body.getTripStatus().equals("2") || (body.getDropStatus().equals("2")))) {
//                Intent intent = new Intent(activity, BusTracking.class);
//                activity.startActivity(intent);
//            } else if ((body.getTripStatus().equals("1"))) {
//                if (body.getDropStatus().equals("1"))
//                showPopUp(view);
//            }else  {
//                showPopUp(view);
//            }
        } else {
            showPopUp(view);
        }
    }

    private void showPopUp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Alert !");
        builder.setMessage("Your Student didn't board any bus.");
        builder.setPositiveButton("Ok", (dialog, id) -> dialog.dismiss());
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.dismiss();
//                }
//            });
        builder.show();
        builder.setCancelable(false);
    }


    public void showLoadingDialog(View view) {

        if (progress == null) {
            progress = new ProgressDialog(view.getContext());
            progress.setTitle(R.string.loading_title);
            progress.setMessage("Loading......");
        }
        progress.show();
        progress.setCancelable(false);
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}
