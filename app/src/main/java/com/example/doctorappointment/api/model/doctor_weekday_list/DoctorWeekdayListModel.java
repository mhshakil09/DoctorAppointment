package com.example.doctorappointment.api.model.doctor_weekday_list;

import android.os.Parcel;
import android.os.Parcelable;

public class DoctorWeekdayListModel implements Parcelable {
    String weekDay;
    String weekDayDetails;

    public DoctorWeekdayListModel(String weekDay, String weekDayDetails) {
        this.weekDay = weekDay;
        this.weekDayDetails = weekDayDetails;
    }

    protected DoctorWeekdayListModel(Parcel in) {
        weekDay = in.readString();
        weekDayDetails = in.readString();
    }

    public static final Creator<com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel> 
            CREATOR = new Creator<com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel>() {
        @Override
        public com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel createFromParcel(Parcel in) {
            return new com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel(in);
        }

        @Override
        public com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel[] newArray(int size) {
            return new com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel[size];
        }
    };

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeekDayDetails() {
        return weekDayDetails;
    }

    public void setWeekDayDetails(String weekDayDetails) {
        this.weekDayDetails = weekDayDetails;
    }

    public static Creator<com.example.doctorappointment.api.model.doctor_weekday_list.DoctorWeekdayListModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "DoctorWeekdayListModel{" +
                "weekDay='" + weekDay + '\'' +
                ", weekDayDetails='" + weekDayDetails + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weekDay);
        dest.writeString(weekDayDetails);
    }
}
