package com.project.entity.enums;

public enum StatusType {
    PENDING("Pending"),
    ACTIVATED("Activated"),
    REJECTED("Rejected");
    public final String statusName;

    StatusType(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
