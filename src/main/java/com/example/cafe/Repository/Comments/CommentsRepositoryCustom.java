package com.example.cafe.Repository.Comments;


import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentsRepositoryCustom {
    public Page<Comments> findCByMemberId(Member member, Pageable pageable);    // Community 댓글 찾기
    public Page<Comments> findAByMemberId(Member member, Pageable pageable);    // Artist 댓글 찾기
}
