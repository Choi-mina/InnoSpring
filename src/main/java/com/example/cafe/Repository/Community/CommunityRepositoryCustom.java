package com.example.cafe.Repository.Community;

import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface CommunityRepositoryCustom {
    public Page<CommunityDto> findAllCommunity(Pageable pageable);
    public CommunityDto findCommunityById(int communityId);
    public List<Community> findByMemberId(Member member);
}
