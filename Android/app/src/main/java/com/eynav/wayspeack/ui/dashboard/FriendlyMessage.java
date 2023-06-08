package com.eynav.wayspeack.ui.dashboard;


public class FriendlyMessage {

    private String text;
    private String name;
    String fullText;
    String dateAdd;
//    private String photoUrl;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name, String fullText, String dateAdd) {
        this.text = text;
        this.name = name;
        this.fullText = fullText;
        this.dateAdd = dateAdd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public String getFullText() {
        return fullText;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
    //    public String getPhotoUrl() {
//        return photoUrl;
//    }
//
//    public void setPhotoUrl(String photoUrl) {
//        this.photoUrl = photoUrl;
//    }
}