package com.example.cafe.Repository.Community;

import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom{
    private final EntityManager em;

    public List<CommunityDto> findAllCommunity() {
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT c, m.email FROM Community c JOIN c.communityAuthor m", Object[].class
            );
            List<Object[]> results = query.getResultList();

            List<CommunityDto> communityDtos = new ArrayList<>();
            for (Object[] result : results) {
                Community community = (Community) result[0];
                String memberEmail = (String) result[1];
                CommunityDto dto = CommunityDto.builder()
                        .communityId(community.getCommunityId())
                        .communityTitle(community.getCommunityTitle())
                        .communityContent(community.getCommunityContent())
                        .author(memberEmail)
                        .createDate(community.getCreateDate())
                        .updateDate(community.getUpdateDate())
                        .build();

                communityDtos.add(dto);  // 리스트에 추가
            }

            return communityDtos;
        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
    }

    public CommunityDto findCommunityById(int communityId) {
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT c, m.email FROM Community c JOIN c.communityAuthor m where c.communityId = :communityId", Object[].class)
                    .setParameter("communityId", communityId);
            List<Object[]> results = query.getResultList();

            CommunityDto communityDto = new CommunityDto();
            for (Object[] result : results) {
                Community community = (Community) result[0];
                String memberEmail = (String) result[1];
                communityDto = CommunityDto.builder()
                        .communityId(community.getCommunityId())
                        .communityTitle(community.getCommunityTitle())
                        .communityContent(community.getCommunityContent())
                        .author(memberEmail)
                        .createDate(community.getCreateDate())
                        .updateDate(community.getUpdateDate())
                        .build();
            }

            return communityDto;
        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }

    }
}
