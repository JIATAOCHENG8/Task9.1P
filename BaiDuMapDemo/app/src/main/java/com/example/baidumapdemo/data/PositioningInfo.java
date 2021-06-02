package com.example.baidumapdemo.data;

public class PositioningInfo {
    private int id;
    private String province;//城市

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double longitude;//经度
    private double latitude;//纬度
    private String name;//位置的地址名字

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
