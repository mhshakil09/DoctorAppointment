package com.example.doctorappointment.api.model.doctor_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DoctorAppointmentModel implements Parcelable {
    List<String> weekDays;
    String startingHour, startingMinute, endingHour, endingMinute, appointmentDuration, breakDuration;

    public DoctorAppointmentModel(List<String> weekDays, String startingHour,String startingMinute, String endingHour,  String endingMinute, String appointmentDuration, String breakDuration) {
        this.weekDays = weekDays;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.endingHour = endingHour;
        this.endingMinute = endingMinute;
        this.appointmentDuration = appointmentDuration;
        this.breakDuration = breakDuration;
    }

    protected DoctorAppointmentModel(Parcel in) {
        weekDays = in.createStringArrayList();
        startingHour = in.readString();
        startingMinute = in.readString();
        endingHour = in.readString();
        endingMinute = in.readString();
        appointmentDuration = in.readString();
        breakDuration = in.readString();
    }

    public static final Creator<DoctorAppointmentModel> CREATOR = new Creator<DoctorAppointmentModel>() {
        @Override
        public DoctorAppointmentModel createFromParcel(Parcel in) {
            return new DoctorAppointmentModel(in);
        }

        @Override
        public DoctorAppointmentModel[] newArray(int size) {
            return new DoctorAppointmentModel[size];
        }
    };

    public DoctorAppointmentModel() {

    }

    @Override
    public String toString() {
        return "DoctorAppointmentModel{" +
                "weekDays=" + weekDays +
                ", startingHour='" + startingHour + '\'' +
                ", startingMinute='" + startingMinute + '\'' +
                ", endingHour='" + endingHour + '\'' +
                ", endingMinute='" + endingMinute + '\'' +
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

    public String getStartingMinute() {
        return startingMinute;
    }

    public void setStartingMinute(String startingMinute) {
        this.startingMinute = startingMinute;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    public String getEndingMinute() {
        return endingMinute;
    }

    public void setEndingMinute(String endingMinute) {
        this.endingMinute = endingMinute;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(weekDays);
        dest.writeString(startingHour);
        dest.writeString(startingMinute);
        dest.writeString(endingHour);
        dest.writeString(endingMinute);
        dest.writeString(appointmentDuration);
        dest.writeString(breakDuration);
    }
}
