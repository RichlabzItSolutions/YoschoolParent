package com.credoapp.parent.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.credoapp.parent.R;
import com.credoapp.parent.adapter.ClassRoutineAdapter;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineRequest;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineResponse;
import com.credoapp.parent.model.classRoutineModels.ClassRoutineResults;
import com.credoapp.parent.retrofit.ITutorSource;
import com.credoapp.parent.utils.Utils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuesdayFragment extends Fragment {
    private List<ClassRoutineResults> classRoutineResults = new ArrayList<>();
    ClassRoutineAdapter mAdapter;
    String adminId,classId,studentId;
    RecyclerView recyclerViewClassRoutine;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getClassRoutineData();
            //recyclerViewClassRoutine.setVisibility(View.GONE);
        }
    }


    public TuesdayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        adminId = getArguments().getString("adminId");
        classId = getArguments().getString("classId");
        studentId = getArguments().getString("studentId");
        recyclerViewClassRoutine = view.findViewById(R.id.recyclerViewClassRoutine);
        return view;
    }


    private void getClassRoutineData() {
//        Utils.showProgressBar(getActivity());
        ClassRoutineRequest request = new ClassRoutineRequest();
        request.setAdminId(adminId);
        request.setClassId(classId);
        request.setStudentId(studentId);
        ITutorSource.getRestAPI().tuesdayClassRoutine(request).enqueue(new Callback<ClassRoutineResponse>() {
            @Override
            public void onResponse(@NonNull Call<ClassRoutineResponse> call, @NonNull Response<ClassRoutineResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("", "onResponse  : " + response);
                    getClassRoutine(Objects.requireNonNull(response.body()));
                    Utils.hideProgressBar();
                }else {
                    Utils.hideProgressBar();
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getActivity(), "server broken try again", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "Un excepted error try again", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ClassRoutineResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Utils.hideProgressBar();
                if (t instanceof SocketTimeoutException){
                    Toast.makeText(getActivity(), "Server internal error try again", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(findViewById(android.R.id.content),"Server internal error try again",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getClassRoutine(ClassRoutineResponse body) {

        Utils.hideProgressBar();
        String response = String.valueOf(body.getCode());
        String description = body.getDescription();

        if ("200".equals(response)) {
            recyclerViewClassRoutine.setVisibility(View.VISIBLE);
            mAdapter = new ClassRoutineAdapter(getActivity(), classRoutineResults);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewClassRoutine.setLayoutManager(mLayoutManager);
            recyclerViewClassRoutine.setItemAnimator(new DefaultItemAnimator());
            recyclerViewClassRoutine.setAdapter(mAdapter);
            classRoutineResults.clear();
            classRoutineResults.addAll(body.getResultsTuesday());
            mAdapter.notifyDataSetChanged();
        } else {
            recyclerViewClassRoutine.setVisibility(View.GONE);
            Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
        }
    }
}
