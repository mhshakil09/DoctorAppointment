package com.example.doctorappointment.ui.patient_details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.doctorappointment.databinding.ActivityPatientDetailsBinding;
import com.example.doctorappointment.databinding.ActivityPatientListBinding;
import com.example.doctorappointment.utils.Helper;

public class PatientDetailsActivity extends AppCompatActivity {

    private ActivityPatientDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initClickListener();
    }

    private void initClickListener() {
        binding.cancelAppointmentBtn.setOnClickListener(v -> {
            Helper.toast(this, "Work in progress");
        });
    }
}