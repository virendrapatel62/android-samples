package com.nvmicrosoftwares.feelfreetocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayList {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imgURL")
    @Expose
    private String imgURL;
    @SerializedName("lectureCount")
    @Expose
    private Integer lectureCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Integer getLectureCount() {
        return lectureCount;
    }

    public void setLectureCount(Integer lectureCount) {
        this.lectureCount = lectureCount;
    }


}