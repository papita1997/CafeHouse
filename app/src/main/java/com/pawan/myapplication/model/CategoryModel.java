package com.pawan.myapplication.model;

public class CategoryModel {
    private int pic;
    private String picUrl;
    private String text;
    private String mealId;

    public CategoryModel(String picUrl, String text, String mealId) {
        this.picUrl = picUrl;
        this.text = text;
        this.mealId = mealId;
    }

    public String getMealId() {
        return mealId;
    }

    public CategoryModel(int pic, String text) {
        this.pic = pic;
        this.text = text;
    }

    public CategoryModel(String picUrl, String text) {
        this.picUrl = picUrl;
        this.text = text;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
