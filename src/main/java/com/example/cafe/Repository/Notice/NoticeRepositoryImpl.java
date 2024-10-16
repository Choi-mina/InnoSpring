package com.example.cafe.Repository.Notice;

import com.example.cafe.entity.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final EntityManager em;

    public List<Notice> findByTitle(String noticeTitle) {
        try {
            TypedQuery<Notice> query = em.createQuery("SELECT n FROM Notice n WHERE n.noticeTitle LIKE :noticeTitle", Notice.class)
                    .setParameter("noticeTitle", "%" + noticeTitle + "%");
            List<Notice> results = query.getResultList();


            if (!results.isEmpty()) {
                return results;
            }
        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
        return null;
    }

    public List<Notice> findByDate(LocalDate startDate, LocalDate endDate) {
        try {
            TypedQuery<Notice> query = em.createQuery(
                            "SELECT n FROM Notice n WHERE FUNCTION('DATE', n.updateDate) BETWEEN :startDate AND :endDate", Notice.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate);
            List<Notice> results = query.getResultList();

            if (!results.isEmpty()) {
                return results;
            }
        } catch (NoResultException e) {
            // 데이터가 없을 때
            return null;
        }
        return null;
    }
}
