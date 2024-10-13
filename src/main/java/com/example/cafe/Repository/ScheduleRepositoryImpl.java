package com.example.cafe.Repository;

import com.example.cafe.entity.Member;
import com.example.cafe.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom{
    private final EntityManager em;

    public List<Schedule>  findByDate(String date) {
        try {
            TypedQuery<Schedule> query = em.createQuery(
                    "SELECT s FROM Schedule s WHERE s.scheduleDate >= :startOfDay AND s.scheduleDate <= :endOfDay",
                    Schedule.class);

            query.setParameter("startOfDay", LocalDate.parse(date).atStartOfDay()); // 날짜의 시작 시간
            query.setParameter("endOfDay", LocalDate.parse(date).atTime(23, 59, 59)); // 날짜의 끝 시간

            List<Schedule> results = query.getResultList();

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
