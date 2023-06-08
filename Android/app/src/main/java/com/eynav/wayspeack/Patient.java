package com.eynav.wayspeack;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Patient  implements Parcelable{
    String dateBirth;
    String dateMeeting;
    String hourMeeting;
    String gender;
    String prescribingClinician;
    String IdentificationNumber;
    String clinicName;

    public Patient( String dateBirth, String gender,String dateMeeting, String hourMeeting, String prescribingClinician, String IdentificationNumber, String clinicName) {
        this.dateBirth = dateBirth;
        this.gender = gender;
        this.dateMeeting = dateMeeting;
        this.hourMeeting = hourMeeting;
        this.prescribingClinician = prescribingClinician;
        this.IdentificationNumber = IdentificationNumber;
        this.clinicName = clinicName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setIdentificationNumber(String identificationNumber) {
        IdentificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return IdentificationNumber;
    }

    public String getPrescribingClinician() {
        return prescribingClinician;
    }

    public String getDateMeeting() {
        return dateMeeting;
    }

    public String getHourMeeting() {
        return hourMeeting;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(dateBirth);
        parcel.writeString(gender);
        parcel.writeString(dateMeeting);
        parcel.writeString(hourMeeting);
        parcel.writeString(prescribingClinician);
        parcel.writeString(IdentificationNumber);
        parcel.writeString(clinicName);

    }

    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    private Patient(Parcel in) {
        dateBirth = in.readString();
        gender= in.readString();
        dateMeeting= in.readString();
        hourMeeting= in.readString();
        prescribingClinician= in.readString();
        IdentificationNumber = in.readString();
        clinicName = in.readString();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "dateBirth='" + dateBirth + '\'' +
                ", dateMeeting='" + dateMeeting + '\'' +
                ", hourMeeting='" + hourMeeting + '\'' +
                ", gender='" + gender + '\'' +
                ", prescribingClinician='" + prescribingClinician + '\'' +
                ", IdentificationNumber='" + IdentificationNumber + '\'' +
                ", clinicName='" + clinicName + '\'' +
                '}';
    }
}
