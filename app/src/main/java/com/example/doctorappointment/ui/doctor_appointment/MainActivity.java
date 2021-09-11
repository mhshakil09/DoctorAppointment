package com.example.doctorappointment.ui.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.doctorappointment.databinding.ActivityMainBinding;
import com.example.doctorappointment.ui.api.model.doctor_appintment.DoctorAppointmentModel;
import com.example.doctorappointment.ui.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    List<String> weekDays = new ArrayList<>();

    String[] startingHourList = {"Starting Hour","3pm","4pm","5pm","6pm","7pm", "8pm","9pm"};
    String[] endingHourList = {"Ending Hour","4pm","5pm","6pm","7pm", "8pm","10pm"};
    String[] appointmentDurationList = {"Select Appointment Duration", "10min","15min","20min","25min","30min"};
    String[] breakDurationList = {"Select Break Duration", "5min","10min","15min","20min","25min", "30min"};

    String startingHour;
    String endingHour;
    String appointmentDuration;
    String breakDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fetchHours();
        fetchAppointmentDuration();
        fetchBreakDuration();

        initClickListener();

    }

    private void initClickListener() {

        binding.submitBtn.setOnClickListener(v -> {
            if (verify()) {
                DoctorAppointmentModel bundle = new DoctorAppointmentModel(weekDays, startingHour, endingHour, appointmentDuration, breakDuration);
                Helper.toast(this ,""+bundle.toString());
            }
        });

    }

    private void fetchWeekDays() {
        if (binding.satDay.isChecked()) {
            weekDays.add("saturday");
        }
        if (binding.sunDay.isChecked()) {
            weekDays.add("sunday");
        }
        if (binding.monDay.isChecked()) {
            weekDays.add("monday");
        }
        if (binding.tueDay.isChecked()) {
            weekDays.add("tuesday");
        }
        if (binding.wedDay.isChecked()) {
            weekDays.add("wednesday");
        }
        if (binding.thursDay.isChecked()) {
            weekDays.add("thursday");
        }
        if (binding.friDay.isChecked()) {
            weekDays.add("friday");
        }
    }

    private void fetchHours() {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, startingHourList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStartingHour.setAdapter(spinnerArrayAdapter);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, endingHourList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEndingHour.setAdapter(arrayAdapter);
    }

    private void fetchAppointmentDuration() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, appointmentDurationList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAppointmentDuration.setAdapter(spinnerArrayAdapter);
    }

    private void fetchBreakDuration() {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, breakDurationList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBreakDuration.setAdapter(spinnerArrayAdapter);
    }
    
    private boolean verify() {
        weekDays.clear();
        fetchWeekDays();
        if (weekDays.isEmpty()) {
            Helper.toast(this, "Please select an Available day");
            return false;
        }


        //region startingHour
        int startingHourSelected = binding.spinnerStartingHour.getSelectedItemPosition();
        startingHour = binding.spinnerStartingHour.getSelectedItem().toString();
        if (startingHourSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Starting hour");
            return false;
        }
        //endregion

        //region EndingHour
        int endingHourSelected = binding.spinnerEndingHour.getSelectedItemPosition();
        endingHour = binding.spinnerEndingHour.getSelectedItem().toString();
        if (endingHourSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Ending Hour");
            return false;
        }
        //endregion

        //region AppointmentDuration
        int appointmentDurationSelected = binding.spinnerAppointmentDuration.getSelectedItemPosition();
        appointmentDuration = binding.spinnerAppointmentDuration.getSelectedItem().toString();
        if (appointmentDurationSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Appointment Duration");
            return false;
        }
        //endregion

        //region BreakDuration
        int breakDurationSelected = binding.spinnerBreakDuration.getSelectedItemPosition();
        breakDuration = binding.spinnerBreakDuration.getSelectedItem().toString();
        if (breakDurationSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Break Duration");
            return false;
        }
        //endregion

        return true;
    }
}