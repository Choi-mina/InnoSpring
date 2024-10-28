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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<CommunityDto> findAllCommunity(Pageable pageable) {
        return communityRepository.findAllCommunity(pageable);
    }

    public CommunityDto findByIdCommunity(int communityId) {
        CommunityDto community = communityRepository.findCommunityById(communityId);
        return community;
    }

    public Page<CommunityDto> findByMemberId(Member member, Pageable pageable) {
        Page<Community> community = communityRepository.findByMemberId(member, pageable);
        List<CommunityDto> communityDtos = new ArrayList<>();

        if(community != null) {
            for(Community c : community) {
                CommunityDto dto = CommunityDto.builder()
                        .communityId(c.getCommunityId())
                        .communityTitle(c.getCommunityTitle())
                        .communityContent(c.getCommunityContent())
                        .createDate(c.getCreateDate())
                        .updateDate(c.getUpdateDate())
                        .build();

                communityDtos.add(dto);  // 리스트에 추가
            }
        }

        return new PageImpl<>(communityDtos, community.getPageable(), community.getTotalElements());
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
