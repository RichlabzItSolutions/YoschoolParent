package com.credoapp.parent.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.credoapp.parent.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.credoapp.parent.common.Constants;
import com.credoapp.parent.common.MultiSelectSpinner;
import com.credoapp.parent.model.donateBooks.ClassList;
import com.credoapp.parent.model.donateBooks.DonateBooksGetRequiredRequest;
import com.credoapp.parent.model.donateBooks.DonateBooksGetRequiredResponse;
import com.credoapp.parent.model.donateBooks.SubmitDonateRequest;
import com.credoapp.parent.model.donateBooks.SubmitDonateResponse;
import com.credoapp.parent.model.donateBooks.SyllabusList;
import com.credoapp.parent.model.donateBooks.UpdateDonateBooksRequest;
import com.credoapp.parent.model.donateBooks.UpdateDonateBooksResponse;
import com.credoapp.parent.model.getUpdeteDonationInfo.GetBooksDonationInfoRequest;
import com.credoapp.parent.model.getUpdeteDonationInfo.GetBooksDonationInfoResponse;
import com.credoapp.parent.retrofit.ITutorSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class DonateBooksActivity extends AppCompatActivity implements MultiSelectSpinner.OnMultipleItemsSelectedListener {
    private ProgressDialog progress;
    private static final String TAG = DonateBooksActivity.class.getSimpleName();
    private EditText editTextAmount, editTextDescription, editTextAmountFree;
    private String amount, studentId, description, adminID, value, donationId, amountFree = "0";
    private RadioButton cashRadioButton;
    private RelativeLayout relativeLayoutAmount, relativeLayoutAmountFree;
    private Spinner syllabusSpinner;
    private RadioButton freeRadioButton;
    private int syllabusPosition;
    private int donationMode = 0;
    private MultiSelectSpinner classesSpinner;
    private ArrayList<String> syllabusesList, syllabusesIdInList, classesList, classesIdInList, selectedClassIdsInList;
    private List<Integer> selectedClasses;
    private TextView bookPicTextView;
    private String imageName, encodedImageLocalPhotos;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_books);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                value = null;
            } else {
                value = extras.getString("value");
                donationId = extras.getString("donationId");
            }
        } else {
            value = (String) savedInstanceState.getSerializable("value");
        }


        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        studentId = sp.getString(Constants.PARENT_ID, studentId);
        adminID = sp.getString(Constants.ADMIN_ID, adminID);


        checkPermissions();

        getDataOfSyllabusClasses();
        LinearLayout backLayoutDonateBooks = findViewById(R.id.back_layout_donate_books);
        backLayoutDonateBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button buttonUpdateDonateBooks = findViewById(R.id.button_update_donate_books);
        editTextAmount = findViewById(R.id.edit_text_amount);
        editTextAmountFree = findViewById(R.id.edit_text_amount_free);
        editTextDescription = findViewById(R.id.edit_text_description);

        Button chooseBookPic = findViewById(R.id.chooseBookPic);
        bookPicTextView = findViewById(R.id.bookPicTextView);

        syllabusSpinner = findViewById(R.id.syllabus_spinner);
        classesSpinner = findViewById(R.id.classes_spinner);

        Button buttonSubmitDonateBooks = findViewById(R.id.button_submit_donate_books);

        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        freeRadioButton = findViewById(R.id.free_radio_button);
        cashRadioButton = findViewById(R.id.cash_radio_button);

        relativeLayoutAmount = findViewById(R.id.relative_layout_amount);
        relativeLayoutAmount.setVisibility(View.GONE);
        relativeLayoutAmountFree = findViewById(R.id.relative_layout_amount_free);
        relativeLayoutAmountFree.setVisibility(View.GONE);


        if (value != null && value.equals("updateDonateBooks")) {
            buttonUpdateDonateBooks.setVisibility(View.VISIBLE);
            buttonSubmitDonateBooks.setVisibility(View.GONE);
        } else {
            buttonUpdateDonateBooks.setVisibility(View.GONE);
            buttonSubmitDonateBooks.setVisibility(View.VISIBLE);
        }

        chooseBookPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);


                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(DonateBooksActivity.this, "You need to accept permission to complete this task", Toast.LENGTH_SHORT).show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            checkPermissions();
                        }
                    }, 2000);

                } else {
                    selectPhoto();
                }
            }
        });

        buttonUpdateDonateBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                description = editTextDescription.getText().toString();
                amount = editTextAmount.getText().toString();
                amountFree = editTextAmountFree.getText().toString();

                if (syllabusPosition == 0) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.pleaseSelectSyllabus, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (selectedClassIdsInList.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Please select classes", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (description.equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.enterDescription, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Log.d("====>", "1");
                    Log.d("====>", "1");
                    Snackbar.make(findViewById(android.R.id.content), R.string.checkPaymentMode, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (cashRadioButton.isChecked()) {
                    donationMode = 2;
                    // is checked
                    if (amountFree.equals("")) {
                        amountFree = "0";
                        Snackbar.make(findViewById(android.R.id.content), R.string.enterAmount, Snackbar.LENGTH_SHORT).show();

                    } else {
                        Log.d("", "");
                    }
                }
                if (freeRadioButton.isChecked()) {
                    donationMode = 1;
                    if (amountFree.equals("")) {
                        amountFree = "0";
                        //Snackbar.make(findViewById(android.R.id.content), R.string.enterAmount, Snackbar.LENGTH_SHORT).show();

                    } else {
                        Log.d("", "");
                    }
                }
                donateBooksUpdate();
            }

        });

        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextAmount.getText().toString().matches("^0")) {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                    editTextAmount.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        cashRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cashRadioButton.isChecked()) {
                    Log.d("====>", " checked");
                    // is checked
                    relativeLayoutAmount.setVisibility(View.VISIBLE);
                    relativeLayoutAmountFree.setVisibility(View.GONE);
                } else {
                    Log.d("====>", "not checked");
                    relativeLayoutAmount.setVisibility(View.GONE);
                    relativeLayoutAmountFree.setVisibility(View.VISIBLE);
                    // not checked
                }
            }
        });
        freeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (freeRadioButton.isChecked()) {
                    Log.d("====>", " checked");
                    // is checked
                    relativeLayoutAmountFree.setVisibility(View.VISIBLE);
                    relativeLayoutAmount.setVisibility(View.GONE);
                } else {
                    Log.d("====>", "not checked");
                    relativeLayoutAmountFree.setVisibility(View.GONE);
                    relativeLayoutAmount.setVisibility(View.VISIBLE);
                    // not checked
                }
            }
        });
        buttonSubmitDonateBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = editTextDescription.getText().toString();
                amount = editTextAmount.getText().toString();
                amountFree = editTextAmountFree.getText().toString();
                if (syllabusPosition == 0) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.pleaseSelectSyllabus, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (selectedClassIdsInList.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Please select classes", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (description.equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), R.string.enterDescription, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Log.d("====>", "1");
                    Log.d("====>", "1");
                    Snackbar.make(findViewById(android.R.id.content), R.string.checkPaymentMode, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (cashRadioButton.isChecked()) {
                    donationMode = 2;
                    // is checked
                    if (amount.equals("")) {
                        Snackbar.make(findViewById(android.R.id.content), R.string.enterAmount, Snackbar.LENGTH_SHORT).show();
                        return;
                    } else {
                        Log.d("", "");
                    }
                }
                if (freeRadioButton.isChecked()) {
                    donationMode = 1;
                    if (amountFree.equals("")) {
                        amountFree = "0";
                        //Snackbar.make(findViewById(android.R.id.content), R.string.enterAmount, Snackbar.LENGTH_SHORT).show();

                    } else {
                        Log.d("", "");
                    }
                }
                donateBooksSubmit();
            }
        });


    }


    private void selectPhoto() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DonateBooksActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else if (options[item].equals("Choose from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        builder.setCancelable(true);
    }

    private void donateBooksUpdate() {
        showLoadingDialog();
        UpdateDonateBooksRequest request = new UpdateDonateBooksRequest();
        String syllabusSelected = syllabusesIdInList.get(syllabusPosition);
        StringBuilder StringBuilderClassIds = new StringBuilder();
        Iterator<String> iter = selectedClassIdsInList.iterator();
        while (iter.hasNext()) {
            StringBuilderClassIds.append(iter.next());
            if (iter.hasNext()) {
                StringBuilderClassIds.append(",");
            }
        }
        Log.d("StringBuilderClassIds", StringBuilderClassIds + "   ....");
        String classIdInString = String.valueOf(StringBuilderClassIds);
        request.setStudentId(studentId);
        request.setClassId(classIdInString);
        request.setSyllabusId(syllabusSelected);
        request.setDescription(description);

        request.setPicture(imageName);
        request.setPictureData(encodedImageLocalPhotos);
        request.setDonateMode(String.valueOf(donationMode));

        if (String.valueOf(donationMode).equals("1")) {
            request.setAmount(amountFree);
        } else {
            request.setAmount(amount);
        }

        request.setDonationId(donationId);
        request.setAdminId(adminID);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().updateDonateBooks(request).enqueue(new Callback<UpdateDonateBooksResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpdateDonateBooksResponse> call, @NonNull Response<UpdateDonateBooksResponse> response) {
                    Log.d(TAG, "onResponse: " + response);

                    dismissLoadingDialog();

                    if (response.isSuccessful()) {
                        updateDonateResponse(Objects.requireNonNull(response.body()));

                    } else {

                        switch (response.code()) {
                            case 404:
                                Toast.makeText(DonateBooksActivity.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(DonateBooksActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(DonateBooksActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<UpdateDonateBooksResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.InternetSettings(DonateBooksActivity.this);
        }
    }

    private void updateDonateResponse(UpdateDonateBooksResponse body) {

        String response = body.getResponseCode();
        String description = body.getDescription();
        if ("200".equals(response)) {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
//            Intent in = new Intent(getApplicationContext(), DonateBooksList.class);
//            finish();
//            startActivity(in);
            onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void donateBooksSubmit() {
        showLoadingDialog();
        SubmitDonateRequest request = new SubmitDonateRequest();
        String syllabusSelected = syllabusesIdInList.get(syllabusPosition);
        StringBuilder StringBuilderClassIds = new StringBuilder();

        Iterator<String> iter = selectedClassIdsInList.iterator();
        while (iter.hasNext()) {
            StringBuilderClassIds.append(iter.next());
            if (iter.hasNext()) {
                StringBuilderClassIds.append(",");
            }
        }
        Log.d("StringBuilderClassIds", StringBuilderClassIds + "   ....");
        String classIdInString = String.valueOf(StringBuilderClassIds);
        request.setStudentId(studentId);
        request.setClassId(classIdInString);
        request.setSyllabusId(syllabusSelected);
        request.setDescription(description);
        request.setPicture(imageName);
        request.setPictureData(encodedImageLocalPhotos);
        request.setDonateMode(String.valueOf(donationMode));
        if (String.valueOf(donationMode).equals("1")) {
            request.setAmount(amountFree);
        } else {
            request.setAmount(amount);
        }
        request.setAdminId(adminID);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().submitDonateResponse(request).enqueue(new Callback<SubmitDonateResponse>() {
                @Override
                public void onResponse(@NonNull Call<SubmitDonateResponse> call, @NonNull Response<SubmitDonateResponse> response) {
                    Log.d(TAG, "onResponse: " + response);

                    dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        submitDonateResponse(Objects.requireNonNull(response.body()));
                    } else {
                        Toast.makeText(DonateBooksActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SubmitDonateResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    dismissLoadingDialog();
                }
            });
        } else {
            Constants.InternetSettings(DonateBooksActivity.this);
        }
    }

    private void submitDonateResponse(SubmitDonateResponse body) {
        String response = body.getResponseCode();
        String description = body.getDescription();
        if ("200".equals(response)) {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
//                Intent in = new Intent(getApplicationContext(), DonateBooksList.class);
//                startActivity(in);
//                finish();
            onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {
        selectedClasses = indices;
        Log.d("selectedClasses===>", selectedClasses + "");
        getOriginalIdsOfClasses(selectedClasses);
    }

    @Override
    public void selectedStrings(List<String> strings) {
    }

    private void getDataOfSyllabusClasses() {
        showLoadingDialog();
        DonateBooksGetRequiredRequest request = new DonateBooksGetRequiredRequest();
        request.setStudentId(studentId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getClassesSyllabus(request).enqueue(new Callback<DonateBooksGetRequiredResponse>() {
                @Override
                public void onResponse(@NonNull Call<DonateBooksGetRequiredResponse> call,
                                       @NonNull Response<DonateBooksGetRequiredResponse> response) {

                    dismissLoadingDialog();
                    if (response.isSuccessful()) {
                        syllabusClassesResponse(Objects.requireNonNull(response.body()));

                    } else {

                        switch (response.code()) {
                            case 404:
                                Toast.makeText(DonateBooksActivity.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(DonateBooksActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(DonateBooksActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<DonateBooksGetRequiredResponse> call, @NonNull Throwable t) {
                }
            });
        } else {
            Constants.haveInternet(DonateBooksActivity.this);
        }

    }

    private void syllabusClassesResponse(DonateBooksGetRequiredResponse body) {

        String response = body.getResponseCode();
        String description = body.getDescription();
        switch (response) {
            case "200":

                getClasses(body);
                getSyllabus(body);

                if (value != null && value.equals("updateDonateBooks")) {
                    getUpdateBookList();
                } else {
                    Log.d("", "");
                }
                break;
            case "204":
                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
                break;
            default:
                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void getClasses(DonateBooksGetRequiredResponse body) {
        List<ClassList> classList = body.getClassList();
        classesList = new ArrayList<>();
        classesIdInList = new ArrayList<>();

        classesIdInList.add(0, "0");
        classesList.add(0, "Classes");
        for (int i = 0; i < classList.size(); i++) {
            String syllabusName = classList.get(i).getClassName();
            classesList.add(syllabusName);
            String syllabusId = classList.get(i).getClassId();
            classesIdInList.add(syllabusId);
        }
        setSpinnerSyllabus();
    }

    private void setSpinnerSyllabus() {
        classesSpinner.setItems(classesList);
        classesSpinner.setSelection(new int[]{0});
        classesSpinner.hasNoneOption(true);
        classesSpinner.setListener(this);
        selectedClasses = classesSpinner.getSelectedIndices();
        getOriginalIdsOfClasses(selectedClasses);
    }

    private void getOriginalIdsOfClasses(List<Integer> selectedClasses) {
        selectedClassIdsInList = new ArrayList<>();
        for (int i = 0; i < selectedClasses.size(); i++) {
            String classSelected = classesIdInList.get(selectedClasses.get(i));
            if (classSelected.equals("0")) {

            } else {
                selectedClassIdsInList.add(classSelected);
            }

//            Log.d("classSelected",classSelected+"");
        }
        Log.d("selectedSubjectIdsIn", selectedClassIdsInList + "     update");
    }

    private void setSpinnerClasses() {

        syllabusesList.add(0, "Syllabus");
        syllabusesIdInList.add(0, "0");
        ArrayAdapter aa = new ArrayAdapter<>(this, R.layout.spinner_item, syllabusesList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        syllabusSpinner.setAdapter(aa);
        syllabusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                syllabusPosition = i;
//                Log.d("syllabusPosition===>", syllabusPosition + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getSyllabus(DonateBooksGetRequiredResponse body) {
        List<SyllabusList> classList = body.getSyllabusLists();
        syllabusesList = new ArrayList<>();
        syllabusesIdInList = new ArrayList<>();
        for (int i = 0; i < classList.size(); i++) {
            String syllabusName = classList.get(i).getSyllabusName();
            syllabusesList.add(syllabusName);
            String syllabusId = classList.get(i).getSyllabusId();
            syllabusesIdInList.add(syllabusId);
        }
        setSpinnerClasses();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void getUpdateBookList() {
        Log.d("response ", "  came to update  \\\\");
        showLoadingDialog();
        GetBooksDonationInfoRequest request = new GetBooksDonationInfoRequest();
        request.setDonation_id(donationId);
//        request.setAdminId(adminId);
        if (Constants.haveInternet(getApplicationContext())) {
            ITutorSource.getRestAPI().getInfoBooks(request).enqueue(new Callback<GetBooksDonationInfoResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetBooksDonationInfoResponse> call, @NonNull Response<GetBooksDonationInfoResponse> response) {
                    Log.d(TAG, "onResponse attendance: " + response);
                    updateBookResponse(Objects.requireNonNull(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<GetBooksDonationInfoResponse> call, @NonNull Throwable t) {
                    dismissLoadingDialog();
                    t.printStackTrace();
                    Log.d("====>", t + "");

                }
            });
        } else {
            Constants.InternetSettings(DonateBooksActivity.this);
        }

    }

    private void updateBookResponse(GetBooksDonationInfoResponse body) {

        String response = body.getCode();
        String description = body.getDescription();
        ArrayList<String> ids = body.getClassIds();

        Log.d("response ", response + "  update  \\\\");
        switch (response) {
            case "200":
                dismissLoadingDialog();
                String getSyllabus_id = body.getDonationBookList().getSyllabus_id();
                String descriptionOfBooks = body.getDonationBookList().getDescription();
                String getAmount = body.getDonationBookList().getAmount();
                String donation_mode = body.getDonationBookList().getDonation_mode();
                Log.d("getSyllabus_id", getSyllabus_id + "  !!!!!!+update");
                String syllabus = getSyllabus_id.trim();
                syllabusSpinner.setSelection(Integer.parseInt(syllabus));
                editTextDescription.setText(descriptionOfBooks);
                if (donation_mode.equals("1")) {
                    freeRadioButton.setChecked(true);
                    editTextAmountFree.setText(getAmount);
                    relativeLayoutAmountFree.setVisibility(View.VISIBLE);
                    relativeLayoutAmount.setVisibility(View.GONE);
                } else {
                    cashRadioButton.setChecked(true);
                    editTextAmount.setText(getAmount);
                    relativeLayoutAmountFree.setVisibility(View.GONE);
                    relativeLayoutAmount.setVisibility(View.VISIBLE);
                }
                setIdes(ids);
                break;
            case "204":
                dismissLoadingDialog();
                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
                break;
            default:
                dismissLoadingDialog();
                Snackbar.make(findViewById(android.R.id.content), description, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void setIdes(ArrayList<String> ids) {
        selectedClassIdsInList = ids;


//        String[] mStringArray = new String[ids.size()];
//        mStringArray = ids.toArray(mStringArray);
//
//        for (String aMStringArray : mStringArray) {
//            Log.d("string is", (String) aMStringArray);
//        }
//
//        Log.d("mStringArray", Arrays.toString(mStringArray) +"");
//
//        Log.d("ids",ids+" /  "+ids.size());

        int[] series = new int[0];

        int values;
        List<Integer> vaus = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Log.d("iiiiii", i + " !!!!");
            values = classesIdInList.indexOf(ids.get(i));
            vaus.add(values);

            series = appendToArrayList(series, values);


//            categoryIdsInt = appendToArrayList(categoryIdsInt, categoryValues);
        }

        classesSpinner.setSelection(series);

        Log.d("vaus", vaus + "   |||||" + Arrays.toString(series));

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri filePathUri;
        Bitmap bitmap;
        switch (requestCode) {
            case 2:
                Log.d("resultCode===>>" + resultCode, "RESULT_OK===>>" + RESULT_OK);
                if (resultCode == RESULT_OK) {
                    filePathUri = data.getData();
                    String url = String.valueOf(filePathUri);
                    File myFile = new File(url);
                    Log.d("url====>", url + " , ");
//                    String realPathFromURI_api19 = getRealPathFromURI_API19(this, filePathUri);
//                    Log.d("Subbu", "onActivityResult: " + realPathFromURI_api19);
                    if (url.startsWith("content://com.google.android.apps.photos.content")) {

                        imageName = myFile.getName();
                        bookPicTextView.setText(imageName);
                        try {
                            InputStream is = DonateBooksActivity.this.getContentResolver().openInputStream(Objects.requireNonNull(filePathUri));
                            if (is != null) {
                                Bitmap pictureBitmap = BitmapFactory.decodeStream(is);
                                //display Image
                                //studentImage.setImageBitmap(pictureBitmap);

                                //You can use this bitmap according to your purpose or Set bitmap to imageview
                            }
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {
                        Log.d("filePathUri====>", filePathUri + "  ,  ");
                        String picturePath = getRealPathFromURI(filePathUri, DonateBooksActivity.this);
                        Log.d("picturePath====>", picturePath + "  ,  ");
                        File f = null;
                        if (picturePath != null) {
                            f = new File(picturePath);
                        }

                        if (f != null) {
                            imageName = f.getName();
                        }
                        Log.d("imageName====>", imageName + "");
                        //uploadProfileImage(imageName);
                        bookPicTextView.setText(imageName);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathUri);
                            //studentImage.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 1:


                //onCaptureImageResult(data);
                Bitmap photo = (Bitmap) (Objects.requireNonNull(data.getExtras())).get("data");
                //studentImage.setImageBitmap(photo);
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = null;
                if (photo != null) {
                    tempUri = getImageUri(getApplicationContext(), photo);
                }
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                if (getRealPathFromCamera(tempUri) != null) {
                    File finalFile = new File(getRealPathFromCamera(tempUri));
                    Log.d("finalFile====>", finalFile + "");

                    imageName = finalFile.getName();

                    //uploadProfileImage(imageName);
                    Log.d("imageName====>", imageName + "");
                    bookPicTextView.setText(imageName);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String getRealPathFromCamera(Uri tempUri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(tempUri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                getImageInBase64(path);
                cursor.close();
            }
        }
        return path;
    }

    private Uri getImageUri(Context applicationContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), photo, "Title", null);
        Log.d("path", path + "      ghgf");
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri filePath, DonateBooksActivity profileActivity) {

        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = profileActivity.managedQuery(filePath, projection, null, null, null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            Log.d("sss===>", s + "");
            getImageInBase64(s);
            // cursor.close();
            return s;
        }

        // cursor.close();
        return null;
    }

    private void getImageInBase64(String s) {
        Bitmap bm = BitmapFactory.decodeFile(s);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        encodedImageLocalPhotos = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        Log.d("image", encodedImageLocalPhotos + "");
    }

    private void checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            int MULTIPLE_PERMISSIONS = 10;
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
        }
    }


    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setTitle(R.string.loading_title);
            progress.setMessage("Loading......");
        }
        progress.show();
        progress.setCancelable(false);
    }

    private void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }


    }


    public static int[] appendToArrayList(int[] var0, int var1) {
        int[] var2;
        if (var0 != null && var0.length != 0) {
            var2 = Arrays.copyOf(var0, var0.length + 1);
        } else {
            var2 = new int[1];
        }
        var2[var2.length - 1] = var1;
        return var2;
    }
}
