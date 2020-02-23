
package com.feelfreetocode.findbestinstitute.webservicesApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("houseName")
    @Expose
    private String houseName;
    @SerializedName("poi")
    @Expose
    private Object poi;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("subSubLocality")
    @Expose
    private String subSubLocality;
    @SerializedName("subLocality")
    @Expose
    private String subLocality;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("type")
    @Expose
    private String type;

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Object getPoi() {
        return poi;
    }

    public void setPoi(Object poi) {
        this.poi = poi;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubSubLocality() {
        return subSubLocality;
    }

    public void setSubSubLocality(String subSubLocality) {
        this.subSubLocality = subSubLocality;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return formattedAddress;
    }
}
