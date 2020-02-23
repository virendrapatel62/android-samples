
package com.feelfreetocode.findbestinstitute.webservicesApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapPlaceResult {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("results")
    @Expose
    private List<Location> results = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Location> getResults() {
        return results;
    }

    public void setResults(List<Location> results) {
        this.results = results;
    }


}
