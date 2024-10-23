package com.example.cafe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int noticeId;
    public String noticeTitle;
    public String noticeContent;
    public Timestamp createDate;
    public Timestamp updateDate;


    @PrePersist
    protected void onCreate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.createDate = currentTimestamp;
        this.updateDate = currentTimestamp;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Timestamp(System.currentTimeMillis());  // Only update the updateDate when the entity is updated
    }
}
