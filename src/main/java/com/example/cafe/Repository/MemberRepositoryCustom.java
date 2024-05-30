package com.example.cafe.Repository;

import com.example.cafe.entity.Member;

public interface MemberRepositoryCustom {
    public Member findByPhone(String phoneNum);
    public Member findByEmail(String email);
}
