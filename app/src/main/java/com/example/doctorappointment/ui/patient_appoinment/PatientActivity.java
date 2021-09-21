package com.example.doctorappointment.ui.patient_appoinment;

import androidx.appcompat.app.AppCompatActivity;
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

    private String selectedDay = "";

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

    }

    private void initTimeSlot() throws ParseException {

        String tempStartingHour = model.getStartingHour();
        String tempEndingHour = model.getEndingHour();
        String tempAppointmentDuration = model.getAppointmentDuration();
        String tempBreakDuration = model.getBreakDuration();
        int startingHour = 3;
        int endingHour = 4;
        int appointmentDuration = 10;
        int breakDuration = 5;

        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(tempStartingHour);
        if (m.find()) {
            startingHour = Integer.parseInt(Objects.requireNonNull(m.group(1)))+12;
        }
        m = p.matcher(tempEndingHour);
        if (m.find()) {
            endingHour = Integer.parseInt(Objects.requireNonNull(m.group(1)))+12;
        }

        m = p.matcher(tempAppointmentDuration);
        if (m.find()) {
            appointmentDuration = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }
        m = p.matcher(tempBreakDuration);
        if (m.find()) {
            breakDuration = Integer.parseInt(Objects.requireNonNull(m.group(1)));
        }

        availableTimeSlotList = getTimeSet(startingHour, endingHour, appointmentDuration, breakDuration);


        initRecyclerView();
    }

    private ArrayList<String> getTimeSet(int startingHour, int endingHour, int appointmentDuration, int breakDuration) throws ParseException {
        ArrayList results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh");

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, startingHour);// what should be the default?
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Calendar endingTime = new GregorianCalendar();
        endingTime.set(Calendar.HOUR_OF_DAY, endingHour);// what should be the default?
        endingTime.set(Calendar.MINUTE, 0);
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

            if (min < 0) {
                temp = "false";
                Timber.d("requestBody false  %s - %s", tempCurr, tempEnd);
                break;
            } else {
                temp = "true";
                Timber.d("requestBody true %s - %s", tempCurr, tempEnd);
                String day = day1 + " - " + day2 + " -> ";
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new PatientAdapter(this, availableTimeSlotList);
        dataAdapter.setClickListener((view, position) ->
                Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+availableTimeSlotList.get(position))
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

        int availableHourSelected = binding.spinnerAvailableDays.getSelectedItemPosition();
        if (availableHourSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Starting hour");
            return false;
        }
        selectedDay = binding.spinnerAvailableDays.getSelectedItem().toString();

        return true;
    }
}