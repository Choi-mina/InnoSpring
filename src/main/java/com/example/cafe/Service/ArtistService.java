package com.example.cafe.Service;

import com.example.cafe.Repository.Artist.ArtistRepository;
import com.example.cafe.dto.*;
import com.example.cafe.entity.Artist;
import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public void saveArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setArtistContent(artistDto.getArtistContent());
        artist.setArtistImage(artistDto.getArtistImage());
        artist.setArtistImagePath(artistDto.getArtistImagePath());

        artistRepository.save(artist);
    }

    public List<ArtistDto> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        List<ArtistDto> artistDtoList = new ArrayList<>();
        if(artists != null)
            for(Artist a : artists) {
                ArtistDto dto = ArtistDto.builder()
                        .artistId(a.getArtistId())
                        .artistContent(a.getArtistContent())
                        .artistImage(a.getArtistImage())
                        .artistImagePath(a.getArtistImagePath())
                        .createDate(a.getCreateDate())
                        .updateDate(a.getUpdateDate())
                        .build();

                artistDtoList.add(dto);  // 리스트에 추가
            }

        return artistDtoList;
    }

    public ArtistDto getArtistById(int artistId) {
        ArtistDto artistDto = new ArtistDto();
        List<CommentsDto> commentsDtos = new ArrayList<>();
        Optional<Artist> artist = artistRepository.findById(Long.valueOf(artistId));
        if(artist != null) {
            for(Comments comments : artist.get().getComments()) {
                CommentsDto commentsDto = new CommentsDto().builder()
                        .commentsId(comments.getCommentsId())
                        .commentsContent(comments.getCommentsContent())
                        .commentsAuthor(comments.getCommentsAuthor())
                        .createDate(comments.getCreateDate())
                        .updateDate(comments.getUpdateDate())
                        .build();
                commentsDtos.add(commentsDto);
            }

            artistDto = ArtistDto.builder()
                    .artistId(artist.get().getArtistId())
                    .artistContent(artist.get().getArtistContent())
                    .artistImage(artist.get().getArtistImage())
                    .artistImagePath(artist.get().getArtistImagePath())
                    .createDate(artist.get().getCreateDate())
                    .updateDate(artist.get().getUpdateDate())
                    .comments(commentsDtos)
                    .build();
        }
        return artistDto;
    }

    public void modifyArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setArtistId(artistDto.getArtistId());
        artist.setArtistContent(artistDto.getArtistContent());
        artist.setArtistImage(artistDto.getArtistImage());
        artist.setArtistImagePath(artistDto.getArtistImagePath());

        artistRepository.save(artist);
    }

    public void delete(int artistId) {
        artistRepository.deleteById((long) artistId);
    }
}
