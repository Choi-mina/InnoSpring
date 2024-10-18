package com.example.cafe.Service;

import com.example.cafe.Repository.Artist.ArtistRepository;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.dto.ScheduleDto;
import com.example.cafe.entity.Artist;
import com.example.cafe.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public void save(ArtistDto artistDto) {
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
                        .build();

                artistDtoList.add(dto);  // 리스트에 추가
            }

        return artistDtoList;
    }
}
