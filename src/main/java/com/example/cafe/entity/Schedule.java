package com.example.cafe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int scheduleID;
    public Timestamp scheduleDate;
    public String scheduleName;
    public String scheduleDescription;
    public Timestamp createDate;
    public Timestamp updateDate;


    @PrePersist
    protected void onCreate() {
        this.createDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }
}
