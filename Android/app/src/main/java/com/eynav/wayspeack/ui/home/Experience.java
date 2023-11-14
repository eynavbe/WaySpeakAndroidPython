package com.eynav.wayspeack.ui.home;

public class Experience {
    String image;
    String date;
    Boolean progress;
    Boolean success;
    String video;

    public Experience(String image, String date,  Boolean success,Boolean progress,String video) {
        this.image = image;
        this.date = date;
        this.success = success;
        this.progress = progress;
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getProgress() {
        return progress;
    }

    public void setProgress(Boolean progress) {
        this.progress = progress;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", progress=" + progress +
                ", success=" + success +
                ", video='" + video + '\'' +
                '}';
    }
}
