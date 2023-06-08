package com.eynav.wayspeack.ui.notifications;

public class TypeResults {
    String type;
    String date;
    String image;

    public TypeResults(String type, String date, String image) {
        this.type = type;
        this.date = date;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TypeResults{" +
                "type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
