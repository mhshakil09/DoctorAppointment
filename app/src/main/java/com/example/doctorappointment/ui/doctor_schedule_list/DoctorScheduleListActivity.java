package com.example.doctorappointment.ui.doctor_schedule_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.doctorappointment.R;
import com.example.doctorappointment.api.model.doctor_appointment.DoctorAppointmentModel;
import com.example.doctorappointment.databinding.ActivityDoctorListBinding;
import com.example.doctorappointment.databinding.ActivityDoctorScheduleListBinding;
import com.example.doctorappointment.ui.doctor_list.DoctorListAdapter;
import com.example.doctorappointment.ui.patient_appoinment.PatientAdapter;
import com.example.doctorappointment.ui.patient_details.PatientDetailsActivity;
import com.example.doctorappointment.utils.Helper;
import com.example.doctorappointment.utils.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class DoctorScheduleListActivity extends AppCompatActivity {

    private ActivityDoctorScheduleListBinding binding;
    private DoctorScheduleListAdapter dataAdapter = new DoctorScheduleListAdapter();

    private DoctorAppointmentModel model = new DoctorAppointmentModel();
    List<String> availableDaysList = new ArrayList<>();
    List<String> availableTimeSlotList = new ArrayList<>();
    List<String> timeSlotList = new ArrayList<>();

    String selectedDay = "";
    String selectedSlot = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorScheduleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SessionManager.init(this);

        initClickListener();

        initFetchTimeSlot();
    }

    private void initFetchTimeSlot() {
        List<String> weekDays = new ArrayList<>();
        weekDays.add("saturday");

        model = new DoctorAppointmentModel(weekDays,
                SessionManager.getStartingHour(), SessionManager.getStartingMinute(),
                SessionManager.getEndingHour(), SessionManager.getEndingMinute(),
                SessionManager.getAppointmentDuration(), SessionManager.getBreakDuration()
        );

        try {
            initTimeSlot();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        Timber.d("requestBody TimeSlot -> %s", availableTimeSlotList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        dataAdapter = new DoctorScheduleListAdapter(this, availableTimeSlotList);
        dataAdapter.setClickListener((view, position) -> {
                    //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+availableTimeSlotList.get(position))
                    selectedSlot = availableTimeSlotList.get(position);
                    SessionManager.setSelectedTimeSlot(this, selectedSlot);
                    Helper.toast(getApplicationContext(), SessionManager.getSelectedTimeSlot());
                    finish();
                }
        );
        recyclerView.setAdapter(dataAdapter);
    }



    private void initClickListener() {
//        binding.submitBtn.setOnClickListener(v -> {
//            if (verify()) {
//                Helper.toast(getApplicationContext(), "Your appointment is on:\n"+selectedDay+", @"+selectedSlot);
//            }
//        });
    }

    private void initTimeSlot() throws ParseException {

        Timber.d("requestBody Model: %s", model);

        String tempStartingHour = model.getStartingHour();
        String tempStartingMinute = model.getStartingMinute();
        String tempEndingHour = model.getEndingHour();
        String tempEndingMinute = model.getEndingMinute();
        String tempAppointmentDuration = model.getAppointmentDuration();
        String tempBreakDuration = model.getBreakDuration();
        int startingHour = 3;
        int endingHour = 4;
        int startingMinute = 0;
        int endingMinute = 0;
        int appointmentDuration = 10;
        int breakDuration = 5;

        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(tempStartingHour);
        if (m.find()) {
            startingHour = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }
        m = p.matcher(tempStartingMinute);
        if (m.find()) {
            startingMinute = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }

        m = p.matcher(tempEndingHour);
        if (m.find()) {
            endingHour = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }
        m = p.matcher(tempEndingMinute);
        if (m.find()) {
            endingMinute = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }

        m = p.matcher(tempAppointmentDuration);
        if (m.find()) {
            appointmentDuration = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }
        m = p.matcher(tempBreakDuration);
        if (m.find()) {
            breakDuration = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }

        Timber.d("requestBody slotting data: startingHour=%s, startingMinute=%s, endingHour=%s, endingMinute=%s",
                startingHour, startingMinute, endingHour, endingMinute);
        availableTimeSlotList.clear();
        availableTimeSlotList = getTimeSet(startingHour, startingMinute, endingHour, endingMinute, appointmentDuration, breakDuration);


        initRecyclerView();
    }

    private ArrayList<String> getTimeSet(int startingHour, int startingMinute, int endingHour, int endingMinute, int appointmentDuration, int breakDuration) throws ParseException {
        ArrayList results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh");

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, startingHour);// what should be the default?
        calendar.set(Calendar.MINUTE, startingMinute);
        calendar.set(Calendar.SECOND, 0);

        Calendar endingTime = new GregorianCalendar();
        endingTime.set(Calendar.HOUR_OF_DAY, endingHour);// what should be the default?
        endingTime.set(Calendar.MINUTE, endingMinute);
        endingTime.set(Calendar.SECOND, 0);

        for (int i = 0; i < 15; i++) {
            String day1 = sdf.format(calendar.getTime());

            // add 15 minutes to the current time; the hour adjusts automatically!
            calendar.add(Calendar.MINUTE, appointmentDuration);

            String day2 = sdf.format(calendar.getTime());
            calendar.add(Calendar.MINUTE, breakDuration);

            //testing start-------------------
            String temp = "";
            int tempCurr = Integer.parseInt(sdf1.format(calendar.getTime()));
            int tempEnd = Integer.parseInt(sdf1.format(endingTime.getTime()));

            Date date1 = sdf.parse(sdf.format(calendar.getTime()));
            Date date2 = sdf.parse(sdf.format(endingTime.getTime()));

            assert date2 != null;
            assert date1 != null;
            long difference = date2.getTime() - date1.getTime();
            long days = (int) (difference / (1000*60*60*24));
            long hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            long min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            hours = (hours < 0 ? -hours : hours);
            Timber.d("requestBody  ======= Hours %s", hours);
            Timber.d("requestBody  ======= Mints %s", min);

//            if (min < 0 || (tempCurr > tempEnd)) {    //Something happened here, so have to think it through again
            if (min < 0) {
                temp = "false";
                Timber.d("requestBody false  %s - %s", tempCurr, tempEnd);
                break;
            } else {
                temp = "true";
                Timber.d("requestBody true %s - %s", tempCurr, tempEnd);
                String day = day1 + " - " + day2;
                results.add(day);
            }
            //testing end  -------------------

            /*String day = day1 + " - " + day2 + " -> " + temp;
            results.add(i, day);*/
        }
        return results;
    }

    private Boolean verify() {

        if (selectedSlot.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select a Time Slot");
            return false;
        }

        return true;
    }
}