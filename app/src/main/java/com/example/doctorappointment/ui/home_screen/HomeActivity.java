package com.example.doctorappointment.ui.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.doctorappointment.R;
import com.example.doctorappointment.api.model.doctor_appointment.DoctorAppointmentModel;
import com.example.doctorappointment.databinding.ActivityDoctorBinding;
import com.example.doctorappointment.databinding.ActivityHomeBinding;
import com.example.doctorappointment.ui.doctor_appointment.DoctorActivity;
import com.example.doctorappointment.ui.doctor_list.DoctorListActivity;
import com.example.doctorappointment.ui.patient_appoinment.PatientActivity;
import com.example.doctorappointment.ui.patient_list.PatientListActivity;
import com.example.doctorappointment.ui.set_schedule.SetScheduleActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initClickListener();

    }

    private void initClickListener() {
        binding.doctorAppointmentBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
                    startActivity(intent);
                }
        );

        binding.patientAppointmentBtn.setOnClickListener(v -> {
            List<String> weekDays = new ArrayList<>();
            weekDays.add("saturday");
            weekDays.add("sunday");

            String startingHour = "15";
            String endingHour = "18";
            String startingMinute = "0";
            String endingMinute = "0";
            String appointmentDuration = "10min";
            String breakDuration = "10min";

            DoctorAppointmentModel model = new DoctorAppointmentModel(weekDays, startingHour, startingMinute, endingHour, endingMinute, appointmentDuration, breakDuration);
            Timber.d("requestBody send %s", model.toString());

            Bundle bundle = new Bundle();
            bundle.putParcelable("model", model);
                    Intent intent = new Intent(getApplicationContext(), PatientActivity.class);
            intent.putExtras(bundle);
                    startActivity(intent);
                }
        );

        binding.setScheduleBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), SetScheduleActivity.class);
                    startActivity(intent);
                }
        );

        binding.patientListBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
                    startActivity(intent);
                }
        );

        binding.doctorListBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), DoctorListActivity.class);
                    startActivity(intent);
                }
        );


    }
}