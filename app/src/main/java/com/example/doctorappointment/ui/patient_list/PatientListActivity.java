package com.example.doctorappointment.ui.patient_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ActivityPatientListBinding;
import com.example.doctorappointment.ui.doctor_appointment.DoctorActivity;
import com.example.doctorappointment.ui.patient_appoinment.PatientAdapter;
import com.example.doctorappointment.ui.patient_details.PatientDetailsActivity;
import com.example.doctorappointment.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {

    private ActivityPatientListBinding binding;
    private PatientListAdapter dataAdapter = new PatientListAdapter();
    List<String> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initPatientList();
        initRecyclerView();
    }

    private void initPatientList() {
        patientList.add("maruf");
        patientList.add("hasan");
        patientList.add("shakil");
    }

    private void initRecyclerView() {


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        dataAdapter = new PatientListAdapter(this, patientList);
        dataAdapter.setClickListener((view, position) -> {
                    Helper.toast(getApplicationContext(), "clicked items position is " + position + " -> " + patientList.get(position));
                    Intent intent = new Intent(getApplicationContext(), PatientDetailsActivity.class);
                    startActivity(intent);
                }
//                selectedSlot = availableTimeSlotList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }
}