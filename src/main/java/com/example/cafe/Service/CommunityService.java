package com.example.cafe.Service;

import com.example.cafe.Repository.Community.CommunityRepository;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.Community;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public void saveCommunity(CommunityDto communityDto) {
        Community community = new Community();
        community.setCommunityTitle(communityDto.getCommunityTitle());
        community.setCommunityContent(communityDto.getCommunityContent());
        community.setCommunityAuthor(communityDto.getCommunityAuthor());
        communityRepository.save(community);
    }

    public List<CommunityDto> findAllCommunity() {
        List<Community> communityList = communityRepository.findAll();
        List<CommunityDto> communityDtoList = new ArrayList<>();
        if(communityList != null)
            for(Community community : communityList) {
                CommunityDto communityDto = CommunityDto.builder()
                        .communityId(community.getCommunityId())
                        .communityTitle(community.getCommunityTitle())
                        .communityContent(community.getCommunityContent())
                        .communityAuthor(community.getCommunityAuthor())
                        .createDate(community.getCreateDate())
                        .updateDate(community.getUpdateDate())
                        .build();

                communityDtoList.add(communityDto);
            }
        return communityDtoList;
    }

    public CommunityDto findByIdCommunity(int communityId) {
        Optional<Community> community = communityRepository.findById(Long.valueOf(communityId));
        CommunityDto communityDto = new CommunityDto();
        if(community != null) {
            communityDto = CommunityDto.builder()
                    .communityId(community.get().getCommunityId())
                    .communityTitle(community.get().getCommunityTitle())
                    .communityContent(community.get().getCommunityContent())
                    .communityAuthor(community.get().getCommunityAuthor())
                    .createDate(community.get().getCreateDate())
                    .updateDate(community.get().getUpdateDate())
                    .build();
        }

        return communityDto;
    }
}
