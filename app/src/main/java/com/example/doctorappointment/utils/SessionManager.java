package com.example.doctorappointment.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SessionManager {

    public static final String SHARED_PREFS = "shared_prefs";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static void setSelectedTimeSlot(Context context, String timeSlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_time_slot", timeSlot);
        editor.apply();
    }

    public static String getSelectedTimeSlot() {
        return sharedPreferences.getString("selected_time_slot", "");
    }

    public static void setSelectedWeekDaySlot(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_week_day_slot", weekDaySlot);
        editor.apply();
    }

    public static String getSelectedWeekDaySlot() {
        return sharedPreferences.getString("selected_week_day_slot", "");
    }

    public static void setSelectedWeekDayDetailsSlot(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_week_day_details_slot", weekDaySlot);
        editor.apply();
    }

    public static String getSelectedWeekDayDetailsSlot() {
        return sharedPreferences.getString("selected_week_day_details_slot", "");
    }

    // for Doctor's appoint schedule
    // weekdays
    public static void setSaturday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_saturday", weekDaySlot);
        editor.apply();
    }

    public static String getSaturday() {
        return sharedPreferences.getString("appointment_on_saturday", "n");
    }

    public static void setSunday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_sunday", weekDaySlot);
        editor.apply();
    }

    public static String getSunday() {
        return sharedPreferences.getString("appointment_on_sunday", "n");
    }

    public static void setMonday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_monday", weekDaySlot);
        editor.apply();
    }

    public static String getMonday() {
        return sharedPreferences.getString("appointment_on_monday", "n");
    }

    public static void setTuesday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_tuesday", weekDaySlot);
        editor.apply();
    }

    public static String getTuesday() {
        return sharedPreferences.getString("appointment_on_tuesday", "n");
    }

    public static void setWednesday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_wednesday", weekDaySlot);
        editor.apply();
    }

    public static String getWednesday() {
        return sharedPreferences.getString("appointment_on_wednesday", "n");
    }

    public static void setThursday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_thursday", weekDaySlot);
        editor.apply();
    }

    public static String getThursday() {
        return sharedPreferences.getString("appointment_on_thursday", "n");
    }

    public static void setFriday(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_on_friday", weekDaySlot);
        editor.apply();
    }

    public static String getFriday() {
        return sharedPreferences.getString("appointment_on_friday", "n");
    }


    //// starting hour, starting minute
    public static void setStartingHour(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_starting_hour", weekDaySlot);
        editor.apply();
    }

    public static String getStartingHour() {
        return sharedPreferences.getString("appointment_starting_hour", "");
    }

    public static void setStartingMinute(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_starting_minute", weekDaySlot);
        editor.apply();
    }

    public static String getStartingMinute() {
        return sharedPreferences.getString("appointment_starting_minute", "");
    }


    // ending hour, ending minute setEndingHour setEndingMinute
    public static void setEndingHour(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_ending_hour", weekDaySlot);
        editor.apply();
    }

    public static String getEndingHour() {
        return sharedPreferences.getString("appointment_ending_hour", "");
    }

    public static void setEndingMinute(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_ending_minute", weekDaySlot);
        editor.apply();
    }

    public static String getEndingMinute() {
        return sharedPreferences.getString("appointment_ending_minute", "");
    }


    // appointmentDuration, breakDuration setAppointmentDuration setBreakDuration
    public static void setAppointmentDuration(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_duration", weekDaySlot);
        editor.apply();
    }

    public static String getAppointmentDuration() {
        return sharedPreferences.getString("appointment_duration", "");
    }

    public static void setBreakDuration(Context context, String weekDaySlot) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("appointment_break_duration", weekDaySlot);
        editor.apply();
    }

    public static String getBreakDuration() {
        return sharedPreferences.getString("appointment_break_duration", "");
    }



}
