package com.example.cafe.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDto {
    private int artistId;
    private MultipartFile artistImageFile;
    private byte[] artistImage;
    private String artistImagePath;
    private String artistContent;
    private Timestamp createDate;
    private Timestamp updateDate;
}
