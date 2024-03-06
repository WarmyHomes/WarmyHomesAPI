package com.project.entity.enums;

public enum AdvertStatusType {
    PENDING(0),
    ACTIVATED(1),
    REJECTED(2);

    public final Integer id;

    AdvertStatusType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
