package com.example.cafe.dto;

import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private int communityId;
    private String communityTitle;
    private String communityContent;
    private String author;
    private Member communityAuthor;
    private Timestamp createDate;
    private Timestamp updateDate;
    private List<CommentsDto> comments;
}
