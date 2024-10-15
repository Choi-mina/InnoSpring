package com.example.cafe.Repository;

import com.example.cafe.entity.Notice;
import com.example.cafe.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long>, NoticeRepositoryCustom {
}
