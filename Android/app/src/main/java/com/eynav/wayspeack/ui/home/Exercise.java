package com.eynav.wayspeack.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.eynav.wayspeack.Patient;

import java.util.ArrayList;
import java.util.List;

public class Exercise  implements Parcelable {
    String dateStart;
    String dateEnd;
    String dateAdd;
    List <TypeExercise> typeExercises;

    public Exercise(String dateStart, String dateEnd, String dateAdd, List<TypeExercise> typeExercises) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateAdd = dateAdd;
        this.typeExercises = typeExercises;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public List<TypeExercise> getTypeExercises() {
        return typeExercises;
    }

    public void setTypeExercises(List<TypeExercise> typeExercises) {
        this.typeExercises = typeExercises;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "dateStart='" + dateStart + '\'' +
                ", dateSEnd='" + dateEnd + '\'' +
                ", dateAdd='" + dateAdd + '\'' +
                ", typeExercises=" + typeExercises +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(dateStart);
        parcel.writeString(dateEnd);
        parcel.writeString(dateAdd);
        parcel.writeList(typeExercises);

    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    private Exercise(Parcel in) {
            dateStart = in.readString();
            dateEnd= in.readString();
            dateAdd= in.readString();
            typeExercises = new ArrayList<TypeExercise>();
            in.readList(typeExercises,TypeExercise.class.getClassLoader());


    }
}
