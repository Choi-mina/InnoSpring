package com.example.cafe.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto implements Serializable {

    private static final long serialVersionUID = 1L;  // Optional but recommended

    private int memId;
    public String userName;
    public String phoneNum;
    public String email;
    public String password;
    public Timestamp createDate;
    public Timestamp updateDate;
    public String flag;
}
