package com.example.cafe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int memId;
    public String userName;
    public String phoneNum;
    public String email;
    public String password;
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
