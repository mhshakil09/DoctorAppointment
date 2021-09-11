package com.example.doctorappointment.ui.api.model.doctor_appintment;

import java.util.List;

public class DoctorAppointmentModel {
    List<String> weekDays;
    String startingHour, endingHour, appointmentDuration, breakDuration;

    public DoctorAppointmentModel(List<String> weekDays, String startingHour, String endingHour, String appointmentDuration, String breakDuration) {
        this.weekDays = weekDays;
        this.startingHour = startingHour;
        this.endingHour = endingHour;
        this.appointmentDuration = appointmentDuration;
        this.breakDuration = breakDuration;
    }

    @Override
    public String toString() {
        return "DoctorAppointmentModel{" +
                "weekDays=" + weekDays +
                ", startingHour='" + startingHour + '\'' +
                ", endingHour='" + endingHour + '\'' +
                ", appointmentDuration='" + appointmentDuration + '\'' +
                ", breakDuration='" + breakDuration + '\'' +
                '}';
    }

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    public String getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(String appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getBreakDuration() {
        return breakDuration;
    }

    public void setBreakDuration(String breakDuration) {
        this.breakDuration = breakDuration;
    }


}
