package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    public String userName;
    public String phoneNum;
    public String email;
    public String password;
    public Timestamp createDate;
    public Timestamp updateDate;
}
