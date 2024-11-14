package com.example.cafe.Repository.Comments;

import com.example.cafe.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsRepositoryCustom {
}
