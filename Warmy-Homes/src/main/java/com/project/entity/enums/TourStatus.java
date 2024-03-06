package com.project.entity.enums;

public enum TourStatus {

    PENDING("Pending"),
    APPROVED("Approved"),
    DECLINED("Rejected"),
    CANCELED("Canceled");
    public final String tourStatusName;


    TourStatus(String tourStatusName) {
        this.tourStatusName = tourStatusName;
    }
    public String getStatusName(){
        return tourStatusName;
    }
}
