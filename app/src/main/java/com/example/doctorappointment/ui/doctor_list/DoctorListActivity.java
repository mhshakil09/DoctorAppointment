package com.example.doctorappointment.ui.doctor_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointment.R;
import com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel;
import com.example.doctorappointment.databinding.ActivityDoctorListBinding;
import com.example.doctorappointment.ui.doctor_schedule_list.DoctorScheduleListActivity;
import com.example.doctorappointment.ui.patient_details.PatientDetailsActivity;
import com.example.doctorappointment.ui.patient_list.PatientListAdapter;
import com.example.doctorappointment.utils.Helper;
import com.example.doctorappointment.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DoctorListActivity extends AppCompatActivity {

    private ActivityDoctorListBinding binding;
    private DoctorListAdapter dataAdapter = new DoctorListAdapter();
    List<DoctorWeekdayListModel> weekDayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SessionManager.init(this);

        initWeekDayList();
        initRecyclerView();
    }

    private void initWeekDayList() {
        weekDayList.add(new DoctorWeekdayListModel("saturday", "01 September, 2021"));
        weekDayList.add(new DoctorWeekdayListModel("sunday", "02 September, 2021"));
        weekDayList.add(new DoctorWeekdayListModel("monday", "03 September, 2021"));
        weekDayList.add(new DoctorWeekdayListModel("tuesday", "04 September, 2021"));
        weekDayList.add(new DoctorWeekdayListModel("wednesday", "05 September, 2021"));
        weekDayList.add(new DoctorWeekdayListModel("thursday", "06 September, 2021"));
    }

    private void initRecyclerView() {


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        dataAdapter = new DoctorListAdapter(this, weekDayList);
        dataAdapter.setClickListener((view, position) -> {
                    Helper.toast(getApplicationContext(), "clicked items position is " + position + " -> " + weekDayList.get(position).getWeekDay());

                    SessionManager.setSelectedWeekDaySlot(this, weekDayList.get(position).getWeekDay());
                    SessionManager.setSelectedWeekDayDetailsSlot(this, weekDayList.get(position).getWeekDayDetails());

                    Intent intent = new Intent(getApplicationContext(), DoctorScheduleListActivity.class);
                    startActivity(intent);
                }
//                selectedSlot = availableTimeSlotList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Helper.toast(getApplicationContext(), SessionManager.getSelectedTimeSlot()+
                "\n"+SessionManager.getSelectedWeekDaySlot()+
                "\n"+SessionManager.getSelectedWeekDayDetailsSlot());
    }
}