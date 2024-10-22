package com.example.cafe.Repository.Community;

import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.Community;

import java.util.List;

public interface CommunityRepositoryCustom {
    public List<CommunityDto> findAllCommunity();
    public CommunityDto findCommunityById(int communityId);
}
