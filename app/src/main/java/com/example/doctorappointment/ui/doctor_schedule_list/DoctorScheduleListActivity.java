package com.example.doctorappointment.ui.doctor_schedule_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.doctorappointment.R;
import com.example.doctorappointment.databinding.ActivityDoctorListBinding;
import com.example.doctorappointment.databinding.ActivityDoctorScheduleListBinding;
import com.example.doctorappointment.ui.doctor_list.DoctorListAdapter;
import com.example.doctorappointment.ui.patient_details.PatientDetailsActivity;
import com.example.doctorappointment.utils.Helper;
import com.example.doctorappointment.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DoctorScheduleListActivity extends AppCompatActivity {

    private ActivityDoctorScheduleListBinding binding;
    private DoctorScheduleListAdapter dataAdapter = new DoctorScheduleListAdapter();
    List<String> timeSlotList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorScheduleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SessionManager.init(this);


        initTimeSlotList();
        initRecyclerView();
    }

    private void initTimeSlotList() {
        timeSlotList.add("12:00 PM - 12:20 PM");
        timeSlotList.add("12:30 PM - 12:50 PM");
        timeSlotList.add("01:00 PM - 01:20 PM");
    }

    private void initRecyclerView() {


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        dataAdapter = new DoctorScheduleListAdapter(this, timeSlotList);
        dataAdapter.setClickListener((view, position) -> {
//                    Helper.toast(getApplicationContext(), "clicked items position is " + position + " -> " + timeSlotList.get(position));
                    SessionManager.setSelectedTimeSlot(this, timeSlotList.get(position));
                    Helper.toast(getApplicationContext(), SessionManager.getSelectedTimeSlot());
                    finish();
                }
//                selectedSlot = availableTimeSlotList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }
}