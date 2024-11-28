package com.credoapp.parent.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.credoapp.parent.R;
import com.credoapp.parent.adapter.FeeReportsTermsAdapter;
import com.credoapp.parent.adapter.FeesAdapter;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.databinding.ActivityFeesDetailsBinding;
import com.credoapp.parent.model.FeeData;
import com.credoapp.parent.model.FeeRequest;
import com.credoapp.parent.model.Fees;
import com.credoapp.parent.model.FeesResponse;
import com.credoapp.parent.model.StudentInfo;
import com.credoapp.parent.model.feeModels.DiscountStatusRequest;
import com.credoapp.parent.model.feeModels.DiscountStatusResponse;
import com.credoapp.parent.model.feeModels.FeeModelsRequest;
import com.credoapp.parent.model.feeModels.FeeModelsResponse;
import com.credoapp.parent.model.feeModels.FeeModelsResults;
import com.credoapp.parent.presenter.ParentPresenter;
import com.credoapp.parent.presenter.StudentPresenter;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;
import com.google.gson.JsonObject;
import com.orhanobut.dialogplus.DialogPlus;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeesDetailsScreen extends ParentActivity implements PaymentResultListener {
    private static final String TAG = FeesDetailsScreen.class.getSimpleName();
    StudentInfo studentInfo;
    int totalMountPaid;
    String adminId,accdomicId;
    private ActivityFeesDetailsBinding binding;
    StudentPresenter studentPresenter;

    private List<FeeModelsResults> feeModelsResults = new ArrayList<>();
    private FeeReportsTermsAdapter mAdapter;
    private String discountStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_fees_details);
        binding = (ActivityFeesDetailsBinding) viewDataBinding;
        Checkout.preload(getApplicationContext());
        setupActionBar("Fees");
        studentInfo = Parcels.unwrap(getIntent().getParcelableExtra("student_info"));
        studentPresenter = new StudentPresenter(this);
//        requestPaidAmount();


        //getStatusOfDiscount();


        binding.payNow.setOnClickListener(v -> {
//                selectPaymentWhichFor()
                    showPopUpNoAccountDetails();
                }
        );


        binding.feeHistory.setOnClickListener(v -> {
            Intent intent = new Intent(FeesDetailsScreen.this, FeeHistoryScreen.class);
            intent.putExtra("student_info", Parcels.wrap(studentInfo));
            startActivity(intent);
        });
    }

    private void getStatusOfDiscount() {
        Utils.showProgressBar(this);
        DiscountStatusRequest request = new DiscountStatusRequest();
        request.setAdminId(adminId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getDiscountStatus(request).enqueue(new Callback<DiscountStatusResponse>() {
                @Override
                public void onResponse(@NonNull Call<DiscountStatusResponse> call, @NonNull Response<DiscountStatusResponse> response) {
                    Log.d(TAG, "onResponse donation list: " + response);
                    Utils.hideProgressBar();
                    if (response.isSuccessful()) {
                        getDiscountStatusResponse(Objects.requireNonNull(response.body()));
                        Utils.hideProgressBar();
                    } else {
                        Toast.makeText(activity, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DiscountStatusResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Utils.hideProgressBar();
                    Toast.makeText(activity, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Utils.hideProgressBar();
            Constants.InternetSettings(FeesDetailsScreen.this);
        }
    }

    private void getDiscountStatusResponse(DiscountStatusResponse body) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        if (response.equals("200")) {
            discountStatus = body.getStatus();
            getPaymentData();
            Utils.hideProgressBar();
//            Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaymentData() {
        Utils.showProgressBar(this);
        FeeModelsRequest request = new FeeModelsRequest();
        request.setStudentId(studentInfo.getStudent_id());//studentInfo.getStudent_id()
        request.setAcademicYear(accdomicId);
//        Toast.makeText(this, accdomicId, Toast.LENGTH_SHORT).show();
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getPaymentsRsponse(request).enqueue(new Callback<FeeModelsResponse>() {
                @Override
                public void onResponse(@NonNull Call<FeeModelsResponse> call, @NonNull Response<FeeModelsResponse> response) {
                    Log.d(TAG, "onResponse donation list: " + response);
                    feeModelsResponse(Objects.requireNonNull(response.body()));
                    Utils.hideProgressBar();
                }

                @Override
                public void onFailure(@NonNull Call<FeeModelsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Utils.hideProgressBar();
                }
            });
        } else {
            Utils.hideProgressBar();
            Constants.InternetSettings(FeesDetailsScreen.this);
        }


    }

    private void feeModelsResponse(FeeModelsResponse body) {

        try{

            String response = body.getCode();
            String description = body.getDescription();

            if (response.equals("200")) {
//            binding.feesDetailsList.setVisibility(View.VISIBLE);
//            feeModelsResults.clear();
//            feeModelsResults.addAll(body.getResults());
//            mAdapter.notifyDataSetChanged();
                if (discountStatus.equals("1")) {
                    binding.discountLayout.setVisibility(View.VISIBLE);
                    binding.totalAmountText.setText(body.getTotalAmount());
                } else {

                    if (body.getTotalAmount().equals("0")) {
                        binding.totalAmountText.setText("0");
                    } else {
                        int bal = Integer.parseInt(body.getTotalAmount()) - Integer.parseInt(body.getTotalDiscount());
                        Log.e(TAG, "feeModelsResponse: " + bal);
                        binding.totalAmountText.setText(String.valueOf(bal));
                    }

                    binding.discountLayout.setVisibility(View.GONE);
                }

                if(body.getTotalAmount()!=null)
                {
                    binding.feesDetailsList.setVisibility(View.VISIBLE);
                    binding.discountAmountText.setText(body.getTotalDiscount());
                    binding.dueAmountText.setText(body.getTotalBalance());
                    binding.paidAmountText.setText(body.getTotalPaid());

                    if (body.getTotalBalance().equals("0")) {
                        binding.payNow.setVisibility(View.GONE);
                    }
                    Log.d(TAG, "getFeeList: toast 200 " + body.getFeeModelsResults().getResults());
                    mAdapter = new FeeReportsTermsAdapter(getApplicationContext(), body.getFeeModelsResults().getResults());
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.feesDetailsList.setLayoutManager(mLayoutManager);
                    binding.feesDetailsList.setItemAnimator(new DefaultItemAnimator());
                    binding.feesDetailsList.setAdapter(mAdapter);
                    Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
                }
               else
                {
                    binding.discountAmountText.setText("");
                    binding.dueAmountText.setText("");
                    binding.paidAmountText.setText("");
                    binding.feesDetailsList.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
                }


            } else {
                binding.feesDetailsList.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex)
        {
            ex.getMessage();
        }


    }

    private void showPopUpNoAccountDetails() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert !!");
        builder.setMessage("Your School/College not updated account details for online payment.");

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    private void requestPaidAmount() {
        Utils.showProgressBar(this);
        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());
        jsonObject.addProperty("student_id", studentInfo.getStudent_id());
        studentPresenter.onRequestFeeDetails(jsonObject);
    }

    List<String> paymentTypes = new ArrayList<>();
    String selectedPaymentType = "";

    private void selectPaymentWhichFor() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, paymentTypes);
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(simpleAdapter)
                .setOnItemClickListener((dialog1, item, view, position) -> {
                    dialog1.dismiss();
                    selectedPaymentType = item.toString();


                    shoePopUp(item.toString());
//                        startPayment();
                })
                .setGravity(Gravity.CENTER)
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }

    private void shoePopUp(String toString) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View mView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.enter_amount, null);

        final EditText enterAmount = mView.findViewById(R.id.enterAmount);
        Button payButton = mView.findViewById(R.id.payButton);
        TextView textViewXVideos = mView.findViewById(R.id.textViewXVideos);


        builder.setView(mView);
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        textViewXVideos.setOnClickListener(view -> dialog.dismiss());

        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (enterAmount.getText().toString().matches("^0")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    enterAmount.setText("");

                }

////                Log.d(TAG, "afterTextChanged:   3");
//                if ((editTextAmountMonthly.getText().toString().length() > 0) &&
//                        (editTextPaidMonthly.getText().toString().length() > 0)) {
////                    Log.d(TAG, "afterTextChanged:   4");
//                    getTotalAmount();
//                } else {
//                    editTextBalanceMonthly.setText("0");//₹ 00.00
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        payButton.setOnClickListener(view -> {

            if (enterAmount.getText().toString().equals("")) {
//                    Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(activity, "Enter Amount", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.dismiss();
            startPayment(toString, enterAmount.getText().toString());


//                if (Patterns.WEB_URL.matcher(englishButton.getText().toString()).matches()){
//                    sendLinkToServer(englishButton.getText().toString());
//                    dialog.dismiss();
//                }else {
//                    Toast.makeText(VideosActivity.this, "Enter Correct Url", Toast.LENGTH_SHORT).show();
//                }
        });


    }

    List<Fees> feesResponseFees;

    public void onSuccessFeeDetails(FeesResponse feesResponse) {


        Log.d(TAG, "onSuccessFeeDetails " + feesResponse.message);

        feesResponseFees = feesResponse.getFees();

        for (Fees fees : feesResponseFees) {
            paymentTypes.add(fees.fee_label_name);
        }

        Utils.hideProgressBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FeesDetailsScreen.this,
                LinearLayoutManager.VERTICAL, false);
        binding.feesDetailsList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.feesDetailsList.getContext(),
                linearLayoutManager.getOrientation());

        if (feesResponse.balance != null)
            binding.payNow.setVisibility(View.INVISIBLE);


        if (feesResponse.total_amount != null)
            binding.totalAmountText.setText("\u20B9 " + feesResponse.total_amount);
        else
            binding.totalAmountText.setText("\u20B9 0");

        if (feesResponse.balance != null)
            binding.dueAmountText.setText("\u20B9 " + feesResponse.balance);
        else
            binding.dueAmountText.setText("\u20B9 0");

        if (feesResponse.paid_amount != null)
            binding.paidAmountText.setText("\u20B9 " + feesResponse.paid_amount);
        else
            binding.paidAmountText.setText("\u20B9 0");
        if (feesResponse.discount != null)
            binding.discountAmountText.setText("\u20B9 " + feesResponse.discount);
        else
            binding.discountAmountText.setText("\u20B9 0");


        FeesAdapter parentStudentsAdapter = new FeesAdapter(this, feesResponseFees);
        binding.feesDetailsList.addItemDecoration(dividerItemDecoration);
        binding.feesDetailsList.setAdapter(parentStudentsAdapter);


    }

    final Activity activity = this;

    public void startPayment(String paymentDescription, String amount) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher);

        /**
         * Reference to current activity
         */


        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Hlm solutions");

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", paymentDescription);

            options.put("currency", "INR");
            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */

            int a = Integer.parseInt(amount);
            totalMountPaid = a * 100;
            options.put("amount", (totalMountPaid));

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razor pay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment status " + s, Toast.LENGTH_LONG).show();
        ParentPresenter parentPresenter = new ParentPresenter(this);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("student_id", "" + studentInfo.getStudent_id());
//        jsonObject.addProperty("amount", "100");
        FeeRequest feeRequest = new FeeRequest();
        feeRequest.setStudent_id(studentInfo.getStudent_id());
        FeeData feeData = new FeeData();
        int feeType = getFeeType(selectedPaymentType);
        if (feeType == -1) return;
        feeData.setFee_type("" + feeType);
//        Amount rs 1 rupee

        feeData.setPaid_amount(String.valueOf(totalMountPaid));
        List<FeeData> feeDataList = new ArrayList<>();
        feeDataList.add(feeData);
        feeRequest.setFeeDataList(feeDataList);
        parentPresenter.onRequestPayment(feeRequest);

    }

    private int getFeeType(String selectedPaymentType) {

        if (selectedPaymentType.isEmpty()) return -1;

        for (Fees fee : feesResponseFees) {
            if (fee.getFee_label_name().equals(selectedPaymentType)) {
                return fee.getFee_type();
            }
        }
        return -1;
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, i + " Payment status " + s, Toast.LENGTH_LONG).show();
    }

    public void onSuccessFeePaid(JsonObject jsonObject) {
        Log.d(TAG, "jsonObject " + jsonObject);
        requestPaidAmount();
    }

    private class SimpleAdapter extends BaseAdapter {
        private final FeesDetailsScreen feesDetailsScreen;
        private final List<String> strings;

        public SimpleAdapter(FeesDetailsScreen feesDetailsScreen, List<String> strings) {
            this.feesDetailsScreen = feesDetailsScreen;
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public String getItem(int position) {
            return strings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(feesDetailsScreen).inflate(R.layout.simple_list_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.text_view.setText(strings.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView text_view;

            public ViewHolder(View view) {
                text_view = view.findViewById(R.id.text_view);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        adminId = sp.getString(Constants.ADMIN_ID, adminId);
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        accdomicId=sp.getString(Constants.DEFAULTACADEMIC_ID,accdomicId);
        getStatusOfDiscount();

    }

}
