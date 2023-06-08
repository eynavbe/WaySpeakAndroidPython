package com.eynav.wayspeack.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TypeExercise  implements Parcelable {
    String type;
    String count;
    String countDo;
    String index;
    List<Experience> experienceList = new ArrayList<>();


    public TypeExercise(String type, String count, String index, String countDo) {
        this.type = type;
        this.count = count;
        this.index = index;
        this.countDo = countDo;
    }
    public TypeExercise(String type, String count, String index, String countDo, List<Experience> experienceList) {
        this.type = type;
        this.count = count;
        this.index = index;
        this.countDo = countDo;
        this.experienceList = experienceList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public String getCountDo() {
        return countDo;
    }

    public void setCountDo(String countDo) {
        this.countDo = countDo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(type);
        parcel.writeString(count);
        parcel.writeString(index);
        parcel.writeString(countDo);


    }

    public static final Parcelable.Creator<TypeExercise> CREATOR = new Parcelable.Creator<TypeExercise>() {
        public TypeExercise createFromParcel(Parcel in) {
            return new TypeExercise(in);
        }

        public TypeExercise[] newArray(int size) {
            return new TypeExercise[size];
        }
    };

    private TypeExercise(Parcel in) {
        type = in.readString();
        count= in.readString();
        index= in.readString();
        countDo = in.readString();


    }


    @Override
    public String toString() {
        return "TypeExercise{" +
                "type='" + type + '\'' +
                ", count='" + count + '\'' +
                ", index=" + index +
                '}';
    }
}
