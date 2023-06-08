package com.eynav.wayspeack;

import android.os.Parcel;
import android.os.Parcelable;

public class Clinician implements Parcelable {
    String email;
    String name;
    String clinicName;
    Long id;

    public Clinician(String email, String name, String clinicName, Long id) {
        this.email = email;
        this.name = name;
        this.clinicName = clinicName;
        this.id =  id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeLong(id);
        parcel.writeString(clinicName);
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getEmail() {
        return email;
    }

    public static final Parcelable.Creator<Clinician> CREATOR = new Parcelable.Creator<Clinician>() {
        public Clinician createFromParcel(Parcel in) {
            return new Clinician(in);
        }

        public Clinician[] newArray(int size) {
            return new Clinician[size];
        }
    };

    private Clinician(Parcel in) {
        name = in.readString();
        email= in.readString();
        id= in.readLong();
        clinicName= in.readString();
    }
}
