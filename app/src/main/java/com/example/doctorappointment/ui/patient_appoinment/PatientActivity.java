package com.example.doctorappointment.ui.patient_appoinment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.doctorappointment.R;
import com.example.doctorappointment.api.model.doctor_appointment.DoctorAppointmentModel;
import com.example.doctorappointment.databinding.ActivityPatientBinding;
import com.example.doctorappointment.utils.Helper;

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


public class PatientActivity extends AppCompatActivity {

    private ActivityPatientBinding binding;

    private DoctorAppointmentModel model = new DoctorAppointmentModel();
    List<String> availableDaysList = new ArrayList<>();
    List<String> availableTimeSlotList = new ArrayList<>();

    String selectedDay = "";
    String selectedSlot = "";

    private PatientAdapter dataAdapter = new PatientAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");

        Timber.d("requestBody Received %s", model);

        initClickListener();
        try {
            initTimeSlot();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fetchAvailableDays();
    }

    private void initClickListener() {
        binding.submitBtn.setOnClickListener(v -> {
            if (verify()) {
                Helper.toast(getApplicationContext(), "Your appointment is on:\n"+selectedDay+", @"+selectedSlot);
            }
        });
    }

    private void initTimeSlot() throws ParseException {

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
            //Timber.d("requestBody  ======= Hours %s", hours);
            //Timber.d("requestBody  ======= Mints %s", min);

            if (min < 0 || (tempCurr > tempEnd)) {
                temp = "false";
                //Timber.d("requestBody false  %s - %s", tempCurr, tempEnd);
                break;
            } else {
                temp = "true";
                //Timber.d("requestBody true %s - %s", tempCurr, tempEnd);
                String day = day1 + " - " + day2;
                results.add(day);
            }
            //testing end  -------------------

            /*String day = day1 + " - " + day2 + " -> " + temp;
            results.add(i, day);*/
        }
        return results;
    }

    private void initRecyclerView() {


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        dataAdapter = new PatientAdapter(this, availableTimeSlotList);
        dataAdapter.setClickListener((view, position) ->
                //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+availableTimeSlotList.get(position))
                selectedSlot = availableTimeSlotList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }

    private void fetchAvailableDays() {
        availableDaysList.clear();
        availableDaysList.add("Select an Appointment Day");
        availableDaysList.addAll(model.getWeekDays());
        Timber.d("requestBody availableDaysList %s", availableDaysList);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, availableDaysList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAvailableDays.setAdapter(spinnerArrayAdapter);
    }

    private Boolean verify() {

        int tempSelectedDays = binding.spinnerAvailableDays.getSelectedItemPosition();
        if (tempSelectedDays == 0) {
            Helper.toast(getApplicationContext(), "Please select a Day");
            return false;
        }
        selectedDay = binding.spinnerAvailableDays.getSelectedItem().toString();

        if (selectedSlot.isEmpty()) {
            Helper.toast(getApplicationContext(), "Please select a Time Slot");
            return false;
        }

        return true;
    }
}