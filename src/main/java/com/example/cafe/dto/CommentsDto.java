package com.example.cafe.dto;

import com.example.cafe.entity.Artist;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private int commentsId;;
    private String commentsContent;
    private Member commentsAuthor;
    private CommunityDto parentCpost;
    private ArtistDto parentApost;
    private Timestamp createDate;
    private Timestamp updateDate;

    private String author;
    private String flag;    // Community 글일 경우 C, Artist 글일 경우 A
    private int communityId;
    private int artistId;
}
