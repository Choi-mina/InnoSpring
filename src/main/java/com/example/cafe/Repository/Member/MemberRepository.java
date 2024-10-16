package com.example.cafe.Repository.Member;

import com.example.cafe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom{

}

