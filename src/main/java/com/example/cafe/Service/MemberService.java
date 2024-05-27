package com.example.cafe.Service;

import com.example.cafe.Repository.MemberRepository;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.ResultEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void memSignUp(MemberDto memberDto) {
        // Dto -> Entity
        Member member = new Member();
        member.setUserName(memberDto.getUserName());
        member.setPhoneNum(memberDto.getPhoneNum());
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());

        memberRepository.save(member);
    }

}
