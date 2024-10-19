package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private int communityId;
    private String communityTitle;
    private String communityContent;
    private String communityAuthor;
    private Timestamp createDate;
    private Timestamp updateDate;
}
