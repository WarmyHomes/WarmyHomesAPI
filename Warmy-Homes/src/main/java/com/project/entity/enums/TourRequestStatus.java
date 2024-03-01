package com.project.entity.enums;

public enum TourRequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    DECLINED("declined"),
    CANCELED("canceled");

    public final String tourRequestStatus_name;

    TourRequestStatus(String tourRequestStatus_name){
        this.tourRequestStatus_name = tourRequestStatus_name;
    }

    public String getTourRequestStatus(){
        return tourRequestStatus_name;
    }
}
