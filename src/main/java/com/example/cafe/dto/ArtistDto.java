package com.example.cafe.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {
    private int artistId;
    private byte[] artistImage;
    private String artistImagePath;
    private String artistContent;
    private Timestamp createDate;
    private Timestamp updateDate;
}
