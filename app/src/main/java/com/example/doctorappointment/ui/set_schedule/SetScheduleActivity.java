package com.example.doctorappointment.ui.set_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.doctorappointment.databinding.ActivitySetScheduleBinding;
import com.example.doctorappointment.databinding.ActivityHomeBinding;
import com.example.doctorappointment.api.model.doctor_appointment.DoctorAppointmentModel;
import com.example.doctorappointment.ui.patient_appoinment.PatientActivity;
import com.example.doctorappointment.utils.Helper;
import com.example.doctorappointment.utils.SessionManager;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.example.doctorappointment.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SetScheduleActivity extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private ActivitySetScheduleBinding binding;

    List<String> weekDays = new ArrayList<>();

    String[] startingHourList = {"Starting Hour","3pm","4pm","5pm","6pm","7pm", "8pm","9pm"};
    String[] endingHourList = {"Ending Hour","4pm","5pm","6pm","7pm", "8pm","9pm","10pm"};
    String[] appointmentDurationList = {"Select Appointment Duration", "10","15","20","25","30"};
    String[] breakDurationList = {"Select Break Duration", "5","10","15","20","25", "30"};

    String startingHour;
    String endingHour;
    String startingMinute;
    String endingMinute;
    String appointmentDuration;
    String breakDuration;

    int defaultStartHour = 12;
    int defaultStartMinute = 00;
    int selectedStartHour = defaultStartHour;
    int selectedStartMinute = defaultStartMinute;

    int defaultEndHour = 15;
    int defaultEndMinute = 00;
    int selectedEndHour = defaultEndHour;
    int selectedEndMinute = defaultEndMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SessionManager.init(this);

        fetchAppointmentDuration();
        fetchBreakDuration();

        initClickListener();
        initSessionManagerData();

        //binding.startHourBtn.setText(defaultStartHour-12+"pm");
        //binding.endHourBtn.setText(defaultEndHour-12+"pm");
        initTextOfButton(defaultStartHour, defaultStartMinute, defaultEndHour, defaultEndMinute);
    }

    private void initSessionManagerData() {
        SessionManager.setSaturday(this, "n");
        SessionManager.setSunday(this, "n");
        SessionManager.setMonday(this, "n");
        SessionManager.setTuesday(this, "n");
        SessionManager.setWednesday(this, "n");
        SessionManager.setThursday(this, "n");
        SessionManager.setFriday(this, "n");

        SessionManager.setStartingHour(this, String.valueOf(defaultStartHour));
        SessionManager.setStartingMinute(this, String.valueOf(defaultStartMinute));
        SessionManager.setEndingHour(this, String.valueOf(defaultEndHour));
        SessionManager.setEndingMinute(this, String.valueOf(defaultEndMinute));

        SessionManager.setAppointmentDuration(this, "0");
        SessionManager.setBreakDuration(this, "0");
    }


    private void initClickListener() {

        binding.startHourBtn.setOnClickListener(v -> {
                    timeRangePicker(0);
                }
        );

        binding.endHourBtn.setOnClickListener(v -> {
                    timeRangePicker(1);
                }
        );

        binding.submitBtn.setOnClickListener(v -> {
            if (verify()) {
                DoctorAppointmentModel model = new DoctorAppointmentModel(weekDays, startingHour, startingMinute, endingHour, endingMinute, appointmentDuration, breakDuration);
                Timber.d("requestBody send %s", model.toString());

                SessionManager.setStartingHour(this, startingHour);
                SessionManager.setStartingMinute(this, startingMinute);
                SessionManager.setEndingHour(this, endingHour);
                SessionManager.setEndingMinute(this, endingMinute);
                SessionManager.setAppointmentDuration(this, appointmentDuration);
                SessionManager.setBreakDuration(this, breakDuration);

                Bundle bundle = new Bundle();
                bundle.putParcelable("model", model);

//                Helper.toast(this, "Work in progress");
//                Helper.toast(this,
                Timber.d(
                    "requestBody Saturday: "+SessionManager.getSaturday()
                            +"\nSunday: "+SessionManager.getSunday()
                            +"\nMonday: "+SessionManager.getMonday()
                            +"\nTuesday: "+SessionManager.getTuesday()
                            +"\nWednesday: "+SessionManager.getWednesday()
                            +"\nThursday: "+SessionManager.getThursday()
                            +"\nFriday: "+SessionManager.getFriday()
                            +"\nFrom: "+SessionManager.getStartingHour()+":"+SessionManager.getStartingMinute()+" - to: "+SessionManager.getEndingHour()+":"+SessionManager.getEndingMinute()
                            +"\nAppointment duration: "+SessionManager.getAppointmentDuration()
                            +"\nBreak duration: "+SessionManager.getBreakDuration());

            }
        });

    }

    private void fetchWeekDays() {
        if (binding.satDay.isChecked()) {
            weekDays.add("saturday");
            SessionManager.setSaturday(this, "y");
        }
        if (binding.sunDay.isChecked()) {
            weekDays.add("sunday");
            SessionManager.setSunday(this, "y");
        }
        if (binding.monDay.isChecked()) {
            weekDays.add("monday");
            SessionManager.setMonday(this, "y");
        }
        if (binding.tueDay.isChecked()) {
            weekDays.add("tuesday");
            SessionManager.setTuesday(this, "y");
        }
        if (binding.wedDay.isChecked()) {
            weekDays.add("wednesday");
            SessionManager.setWednesday(this, "y");
        }
        if (binding.thursDay.isChecked()) {
            weekDays.add("thursday");
            SessionManager.setThursday(this, "y");
        }
        if (binding.friDay.isChecked()) {
            weekDays.add("friday");
            SessionManager.setFriday(this, "y");
        }
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
        startingHour = selectedStartHour+"";
        startingMinute = selectedStartMinute+"";
        //endregion

        //region EndingHour
        endingHour = selectedEndHour+"";
        endingMinute = selectedEndMinute+"";
        //endregion

        Timber.d("requestBody selectedTime, startingHour=%s, startingMinute=%s, endingHour=%s, endingMinute=%s",
                startingHour, startingMinute, endingHour, endingMinute);

        //region AppointmentDuration
        int appointmentDurationSelected = binding.spinnerAppointmentDuration.getSelectedItemPosition();
        if (appointmentDurationSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Appointment Duration");
            return false;
        }
        appointmentDuration = binding.spinnerAppointmentDuration.getSelectedItem().toString();
        //endregion

        //region BreakDuration
        int breakDurationSelected = binding.spinnerBreakDuration.getSelectedItemPosition();
        if (breakDurationSelected == 0) {
            Helper.toast(getApplicationContext(), "Please select a Break Duration");
            return false;
        }
        breakDuration = binding.spinnerBreakDuration.getSelectedItem().toString();
        //endregion

        return true;
    }

    private void timeRangePicker(int selectedTab) {

        RangeTimePickerDialog dialog = new RangeTimePickerDialog();

        dialog.newInstance();
        if (selectedTab == 0) {
            dialog.setInitialOpenedTab(RangeTimePickerDialog.InitialOpenedTab.START_CLOCK_TAB);
        } else {
            dialog.setInitialOpenedTab(RangeTimePickerDialog.InitialOpenedTab.END_CLOCK_TAB);
        }
        if (selectedStartHour == 0 || selectedEndHour == 0) {
            dialog.setInitialStartClock(defaultStartHour, defaultStartMinute);
            dialog.setInitialEndClock(defaultEndHour, defaultEndMinute);
        } else {
            dialog.setInitialStartClock(selectedStartHour, selectedStartMinute);
            dialog.setInitialEndClock(selectedEndHour, selectedEndMinute);
        }
        dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
        dialog.setIs24HourView(false); // Indicates if the format should be 24 hours
        dialog.setColorBackgroundHeader(R.color.colorPrimary); // Set Color of Background header dialog
        dialog.setColorTextButton(R.color.colorPrimaryDark); // Set Text color of button
        dialog.enableMinutes(true);
        dialog.setValidateRange(true);
        dialog.setMessageErrorRangeTime("End time should be Greater than the starting time");

        Timber.d("requestBody Selected StartHour: "+selectedStartHour+":"+selectedStartMinute+", EndHour: "+selectedEndHour+":"+selectedEndMinute);
        FragmentManager fragmentManager = getFragmentManager();
        dialog.show(fragmentManager, "");
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        // Use parameters provided by Dialog
        initTextOfButton(hourStart, minuteStart, hourEnd, minuteEnd);

        selectedStartHour = hourStart;
        selectedStartMinute = minuteStart;
        selectedEndHour = hourEnd;
        selectedEndMinute = minuteEnd;
        Timber.d("requestBody Start: "+selectedStartHour+":"+selectedStartMinute+", End: "+selectedEndHour+":"+selectedEndMinute);

        //Helper.toast(this, "Start: "+hourStart+":"+minuteStart+"\nEnd: "+hourEnd+":"+minuteEnd);
    }

    private void initTextOfButton(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        String startText;
        if (hourStart>12) {
            startText = (hourStart-12)+":"+minuteStart+ "pm";
        }
        else if (hourStart == 12) {
            startText = (hourStart)+":"+minuteStart+ "pm";
        }
        else {
            startText = (hourStart)+":"+minuteStart+ "am";
        }
        binding.startHourBtn.setText(startText);

        String endText;
        if (hourEnd>12) {
            endText = (hourEnd-12)+":"+minuteEnd+ "pm";
        }
        else if (hourEnd == 12) {
            endText = (hourEnd)+":"+minuteEnd+ "pm";
        }
        else {
            endText = (hourEnd)+":"+minuteEnd+ "am";
        }
        binding.endHourBtn.setText(endText);
    }
}