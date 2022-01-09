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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

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

        getSystemDate();

//        initWeekDayList();
        initRecyclerView();
    }

    private void setWeekDayList(String day, Date date) {

        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        String formattedDate = df.format(date);
        Timber.d("requestBody date is: %s", formattedDate);

        weekDayList.add(new DoctorWeekdayListModel(day, formattedDate));
    }

    void checkDate(Date date) {
        Timber.d("requestBody Check:     " + date);
//        Date dt = new Date();
//        String tempDay = dt.toString().substring(0, 3);
        String tempDay = date.toString().substring(0, 3);
        while (true){
            if (tempDay.contains("Sat") && SessionManager.getSaturday().equals("y")) {
                Timber.d("requestBody match Sat");

                setWeekDayList("Saturday", date);
                break;
            } else  if(tempDay.contains("Sun") && SessionManager.getSunday().equals("y")) {
                Timber.d("requestBody match Sun");

                setWeekDayList("Sunday", date);
                break;
            } else  if(tempDay.contains("Mon") && SessionManager.getMonday().equals("y")) {
                Timber.d("requestBody match Mon");

                setWeekDayList("Monday", date);
                break;
            } else  if(tempDay.contains("Tue") && SessionManager.getTuesday().equals("y")) {
                Timber.d("requestBody match Tue");

                setWeekDayList("Tuesday", date);
                break;
            } else  if(tempDay.contains("Wed") && SessionManager.getWednesday().equals("y")) {
                Timber.d("requestBody match Wed");

                setWeekDayList("Wednesday", date);
                break;
            } else  if(tempDay.contains("Thu") && SessionManager.getThursday().equals("y")) {
                Timber.d("requestBody match Thu");

                setWeekDayList("Thursday", date);
                break;
            } else  if(tempDay.contains("Fri") && SessionManager.getFriday().equals("y")) {
                Timber.d("requestBody match Fri");

                setWeekDayList("Friday", date);
                break;
            } else {
                break;
            }
        }
    }

    private void getSystemDate() {

        Date dt = new Date();
        Timber.d("requestBody %s", dt.toString().substring(0, 3));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, 1);
        dt = calendar.getTime();
//        Timber.d("requestBody Day 3:     %s", dt);


//        checkDate(dt.toString().substring(0, 3));
        Timber.d("requestBody-------------------------------------------");
        for( int i = 1 ; i < 7 ; i++) {
            checkDate(dt);
            calendar.add(Calendar.DATE, 1);
            dt = calendar.getTime();
        }
        Timber.d("requestBody-------------------------------------------");
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