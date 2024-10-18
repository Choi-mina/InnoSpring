package com.example.cafe.controller;

import com.example.cafe.Service.ArtistService;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {
    final private ArtistService artistService;

    @PostMapping("/save")
    @ResponseBody
    public ResultEntity saveArtist(
            @RequestPart("artistDto") ArtistDto artistDto,
            @RequestParam(value = "artistImage", required = false) MultipartFile artistImage) {
        ResultEntity result = new ResultEntity();
        try {
            if (artistImage != null) {
                artistDto.setArtistImage(artistImage.getBytes());
                artistDto.setArtistImagePath(artistImage.getOriginalFilename()); // Save the file name as path
            }
            artistService.saveArtist(artistDto);
            // artist save success
            result.setCode("0000");
            result.setMessage("Artist Save Success");
            log.info("Artist Save Success");
        } catch (Exception e) {
            log.error("Artist-save Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @GetMapping("/find-all")
    public ResultEntity findAllArtist() {
        ResultEntity result = new ResultEntity();
        try {
            List<ArtistDto> artistDtos = artistService.getAllArtists();
            // artist findAll success
            result.setCode("0000");
            result.setMessage("Artist FindAll Success");
            result.setData(artistDtos);
            log.info("Artist FindAll Success");
        } catch (Exception e) {
            log.error("Artist-FindAll Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @GetMapping("/find-by-id")
    public ResultEntity findArtistById(@RequestParam("id") int id) {
        ResultEntity result = new ResultEntity();
        try {
            ArtistDto artistDto = artistService.getArtistById(id);
            // artist findById success
            result.setCode("0000");
            result.setMessage("Artist findById Success");
            result.setData(artistDto);
            log.info("Artist findById Success");
        } catch (Exception e) {
            log.error("Artist-findById Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @PostMapping("/modify")
    @ResponseBody
    public ResultEntity modifyArtist(
            @RequestPart("artistDto") ArtistDto artistDto,
            @RequestParam(value = "artistImage", required = false) MultipartFile artistImage) {
        ResultEntity result = new ResultEntity();
        try {
            if (artistImage != null) {
                artistDto.setArtistImage(artistImage.getBytes());
                artistDto.setArtistImagePath(artistImage.getOriginalFilename()); // Save the file name as path
            }
            artistService.modifyArtist(artistDto);
            // artist modify success
            result.setCode("0000");
            result.setMessage("Artist modify Success");
            log.info("Artist modify Success");
        } catch (Exception e) {
            log.error("Artist-modify Fail");
            return new ResultEntity(e);
        }

        return result;
    }

    @PostMapping("/delete")
    public ResultEntity deleteArtist(@RequestParam("id") int id) {
        ResultEntity result = new ResultEntity();
        try {
            artistService.delete(id);
            // artist delete success
            result.setCode("0000");
            result.setMessage("Artist delete Success");
            log.info("Artist delete Success");
        } catch (Exception e) {
            log.error("Artist-delete Fail");
            return new ResultEntity(e);
        }

        return result;
    }

}
