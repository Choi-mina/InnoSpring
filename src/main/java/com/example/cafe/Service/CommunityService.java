package com.example.cafe.Service;

import com.example.cafe.Repository.Community.CommunityRepository;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
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
        List<CommunityDto> communityDtoList = communityRepository.findAllCommunity();
        return communityDtoList;
    }

    public CommunityDto findByIdCommunity(int communityId) {
        CommunityDto community = communityRepository.findCommunityById(communityId);
        return community;
    }

    public List<CommunityDto> findByMemberId(Member member) {
        List<Community> community = communityRepository.findByMemberId(member);
        List<CommunityDto> communityDtos = new ArrayList<>();

        if(community != null) {
            for(int i = community.size() - 1; i >= 0; i--) {
                CommunityDto dto = CommunityDto.builder()
                        .communityId(community.get(i).getCommunityId())
                        .communityTitle(community.get(i).getCommunityTitle())
                        .communityContent(community.get(i).getCommunityContent())
                        .createDate(community.get(i).getCreateDate())
                        .updateDate(community.get(i).getUpdateDate())
                        .build();

                communityDtos.add(dto);  // 리스트에 추가
            }
        }

        return communityDtos;
    }

    public void modifyCommunity(CommunityDto communityDto) {
        Community community = new Community();
        community.setCommunityId(communityDto.getCommunityId());
        community.setCommunityTitle(communityDto.getCommunityTitle());
        community.setCommunityContent(communityDto.getCommunityContent());
        community.setCommunityAuthor(communityDto.getCommunityAuthor());
        communityRepository.save(community);
    }

    public void deleteCommunity(int communityId) {
        communityRepository.deleteById((long) communityId);
    }
}
