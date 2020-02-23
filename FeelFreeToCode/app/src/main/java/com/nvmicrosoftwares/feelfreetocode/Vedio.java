
package com.nvmicrosoftwares.feelfreetocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vedio {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imgURL")
    @Expose
    private String imgURL;
    @SerializedName("vedioURL")
    @Expose
    private String vedioURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getVedioURL() {
        return vedioURL;
    }

    public void setVedioURL(String vedioURL) {
        this.vedioURL = vedioURL;
    }

}
