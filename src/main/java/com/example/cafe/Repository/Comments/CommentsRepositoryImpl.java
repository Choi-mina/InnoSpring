package com.example.cafe.Repository.Comments;

import com.example.cafe.Repository.Community.CommunityRepositoryCustom;
import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CommentsRepositoryImpl implements CommentsRepositoryCustom {
    private final EntityManager em;

    public Page<Comments> findCByMemberId(Member member, Pageable pageable) {
        try {
            TypedQuery<Comments> query = em.createQuery("SELECT c FROM Comments c where c.commentsAuthor = :commentsAuthor and c.parentApost IS NULL ORDER BY c.updateDate DESC", Comments.class)
                    .setParameter("commentsAuthor", member);

            // 페이지 범위 설정
            query.setFirstResult((int) pageable.getOffset()); // 시작 위치
            query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

            List<Comments> results = query.getResultList();

            // 전체 개수 조회 (페이징 처리에 필요)
            TypedQuery<Long> countQuery = em.createQuery(
                            "SELECT COUNT(c) FROM Comments c where c.commentsAuthor = :commentsAuthor and c.parentApost IS NULL", Long.class)
                    .setParameter("commentsAuthor", member);;
            long total = countQuery.getSingleResult();

            return new PageImpl<>(results, pageable, total);

        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
    }

    public Page<Comments> findAByMemberId(Member member, Pageable pageable) {
        try {
            TypedQuery<Comments> query = em.createQuery("SELECT c FROM Comments c where c.commentsAuthor = :commentsAuthor and c.parentCpost IS NULL ORDER BY c.updateDate DESC", Comments.class)
                    .setParameter("commentsAuthor", member);

            // 페이지 범위 설정
            query.setFirstResult((int) pageable.getOffset()); // 시작 위치
            query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

            List<Comments> results = query.getResultList();

            // 전체 개수 조회 (페이징 처리에 필요)
            TypedQuery<Long> countQuery = em.createQuery(
                            "SELECT COUNT(c) FROM Comments c where c.commentsAuthor = :commentsAuthor and c.parentCpost IS NULL", Long.class)
                    .setParameter("commentsAuthor", member);;
            long total = countQuery.getSingleResult();

            return new PageImpl<>(results, pageable, total);

        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
    }
}
