package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private int memId;
    public String userName;
    public String phoneNum;
    public String email;
    public String password;
    public Timestamp createDate;
    public Timestamp updateDate;
    public String flag;
}
