package com.ss.scrumptious_restaurant.entity;

public enum PreparationStatus{
    PENDING("PENDING"),
    CANCEL("CANCEL"),
    READY("READY"),
    COMPLETE("COMPLETE");

    private final String status;

    PreparationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return getStatus();
    }

}
