package com.example.cafe.Repository.Community;

import com.example.cafe.dto.CommentsDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom{
    private final EntityManager em;

    public Page<CommunityDto> findAllCommunity(Pageable pageable) {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT c, m.email FROM Community c JOIN c.communityAuthor m ORDER BY c.updateDate DESC", Object[].class
        );

        // 페이지 범위 설정
        query.setFirstResult((int) pageable.getOffset()); // 시작 위치
        query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

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

            communityDtos.add(dto);
        }

        // 전체 개수 조회 (페이징 처리에 필요)
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(c) FROM Community c JOIN c.communityAuthor m", Long.class
        );
        long total = countQuery.getSingleResult();

        return new PageImpl<>(communityDtos, pageable, total);
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

                List<CommentsDto> commentsDtos = new ArrayList<>();

                for(Comments comment : community.getComments()) {
                    CommentsDto commentsDto = new CommentsDto().builder()
                            .commentsId(comment.getCommentsId())
                            .commentsContent(comment.getCommentsContent())
                            .commentsAuthor(comment.getCommentsAuthor())
                            .createDate(comment.getCreateDate())
                            .updateDate(comment.getUpdateDate())
                            .build();
                    commentsDtos.add(commentsDto);
                }

                communityDto = CommunityDto.builder()
                        .communityId(community.getCommunityId())
                        .communityTitle(community.getCommunityTitle())
                        .communityContent(community.getCommunityContent())
                        .author(memberEmail)
                        .createDate(community.getCreateDate())
                        .updateDate(community.getUpdateDate())
                        .comments(commentsDtos)
                        .build();
            }

            return communityDto;
        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
    }

    public Page<Community> findByMemberId(Member member, Pageable pageable) {
        try {
            TypedQuery<Community> query = em.createQuery("SELECT c FROM Community c where c.communityAuthor = :communityAuthor ORDER BY c.updateDate DESC", Community.class)
                    .setParameter("communityAuthor", member);

            // 페이지 범위 설정
            query.setFirstResult((int) pageable.getOffset()); // 시작 위치
            query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

            List<Community> results = query.getResultList();

            // 전체 개수 조회 (페이징 처리에 필요)
            TypedQuery<Long> countQuery = em.createQuery(
                    "SELECT COUNT(c) FROM Community c where c.communityAuthor = :communityAuthor", Long.class)
                    .setParameter("communityAuthor", member);;
            long total = countQuery.getSingleResult();

            return new PageImpl<>(results, pageable, total);

        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }

    }
}
