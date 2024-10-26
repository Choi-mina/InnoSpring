package com.example.cafe.Repository.Notice;

import com.example.cafe.dto.CommunityDto;
import com.example.cafe.dto.NoticeDto;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final EntityManager em;

    public Page<NoticeDto> findAllNotice(Pageable pageable) {
        TypedQuery<Notice> query = em.createQuery(
                "SELECT n FROM Notice n ORDER BY n.updateDate DESC", Notice.class
        );

        // 페이지 범위 설정
        query.setFirstResult((int) pageable.getOffset()); // 시작 위치
        query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

        List<Notice> results = query.getResultList();
        List<NoticeDto> noticeDtos = new ArrayList<>();

        if(!results.isEmpty()){
            for (Notice result : results) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(result.getNoticeId())
                        .noticeTitle(result.getNoticeTitle())
                        .noticeContent(result.getNoticeContent())
                        .createDate(result.getCreateDate())
                        .updateDate(result.getUpdateDate())
                        .build();

                noticeDtos.add(dto);
            }
        }

        // 전체 개수 조회 (페이징 처리에 필요)
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(n) FROM Notice n", Long.class
        );
        long total = countQuery.getSingleResult();

        return new PageImpl<>(noticeDtos, pageable, total);
    }

    public Page<NoticeDto> findByTitle(String noticeTitle, Pageable pageable) {
        TypedQuery<Notice> query = em.createQuery("SELECT n FROM Notice n WHERE n.noticeTitle LIKE :noticeTitle ORDER BY n.updateDate DESC", Notice.class)
                .setParameter("noticeTitle", "%" + noticeTitle + "%");

        // 페이지 범위 설정
        query.setFirstResult((int) pageable.getOffset()); // 시작 위치
        query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

        List<Notice> results = query.getResultList();
        List<NoticeDto> noticeDtos = new ArrayList<>();

        if(!results.isEmpty()){
            for (Notice result : results) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(result.getNoticeId())
                        .noticeTitle(result.getNoticeTitle())
                        .noticeContent(result.getNoticeContent())
                        .createDate(result.getCreateDate())
                        .updateDate(result.getUpdateDate())
                        .build();

                noticeDtos.add(dto);
            }
        }

        // 전체 개수 조회 (페이징 처리에 필요)
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(n) FROM Notice n", Long.class
        );
        long total = countQuery.getSingleResult();

        return new PageImpl<>(noticeDtos, pageable, total);
    }

    public Page<NoticeDto> findByDate(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        TypedQuery<Notice> query = em.createQuery(
                        "SELECT n FROM Notice n WHERE FUNCTION('DATE', n.updateDate) BETWEEN :startDate AND :endDate ORDER BY n.updateDate DESC", Notice.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);

        // 페이지 범위 설정
        query.setFirstResult((int) pageable.getOffset()); // 시작 위치
        query.setMaxResults(pageable.getPageSize()); // 페이지 크기 설정

        List<Notice> results = query.getResultList();
        List<NoticeDto> noticeDtos = new ArrayList<>();

        if(!results.isEmpty()){
            for (Notice result : results) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(result.getNoticeId())
                        .noticeTitle(result.getNoticeTitle())
                        .noticeContent(result.getNoticeContent())
                        .createDate(result.getCreateDate())
                        .updateDate(result.getUpdateDate())
                        .build();

                noticeDtos.add(dto);
            }
        }

        // 전체 개수 조회 (페이징 처리에 필요)
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(n) FROM Notice n", Long.class
        );
        long total = countQuery.getSingleResult();

        return new PageImpl<>(noticeDtos, pageable, total);
    }
}
