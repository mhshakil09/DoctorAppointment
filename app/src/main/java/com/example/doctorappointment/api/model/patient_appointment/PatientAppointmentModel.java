package com.example.doctorappointment.api.model.patient_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PatientAppointmentModel implements Parcelable {
    String selectedDay;
    String selectedTime;

    public PatientAppointmentModel(String selectedDay, String selectedTime) {
        this.selectedDay = selectedDay;
        this.selectedTime = selectedTime;
    }

    protected PatientAppointmentModel(Parcel in) {
        selectedDay = in.readString();
        selectedTime = in.readString();
    }

    public static final Creator<PatientAppointmentModel> CREATOR = new Creator<PatientAppointmentModel>() {
        @Override
        public PatientAppointmentModel createFromParcel(Parcel in) {
            return new PatientAppointmentModel(in);
        }

        @Override
        public PatientAppointmentModel[] newArray(int size) {
            return new PatientAppointmentModel[size];
        }
    };

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public static Creator<PatientAppointmentModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "PatientAppointmentModel{" +
                "selectedDay='" + selectedDay + '\'' +
                ", selectedTime='" + selectedTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedDay);
        dest.writeString(selectedTime);
    }
}
