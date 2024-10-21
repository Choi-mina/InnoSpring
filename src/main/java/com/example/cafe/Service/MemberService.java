package com.example.cafe.Service;

import com.example.cafe.Repository.Member.MemberRepository;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        member.setFlag(memberDto.getFlag());

        memberRepository.save(member);
    }
    
    public MemberDto findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        MemberDto memberDto = null;
        // Entity -> Dto
        if(member.isPresent())
            memberDto = MemberDto.builder()
                    .userName(member.get().getUserName())
                    .email(member.get().getEmail())
                    .phoneNum(member.get().getPhoneNum())
                    .password(member.get().getPassword())
                    .createDate(member.get().getCreateDate())
                    .updateDate(member.get().getUpdateDate())
                    .flag(member.get().getFlag())
                    .build();
        return memberDto;
    }

    public MemberDto findByPhoneNum(String phoneNum) {
        Member member = memberRepository.findByPhone(phoneNum);
        // Entity -> Dto
        MemberDto memberDto = null;
        if(member != null) {
            memberDto = MemberDto.builder()
                    .userName(member.getUserName())
                    .email(member.getEmail())
                    .phoneNum(member.getPhoneNum())
                    .password(member.getPassword())
                    .createDate(member.getCreateDate())
                    .updateDate(member.getUpdateDate())
                    .flag(member.getFlag())
                    .build();
        }
        return memberDto;
    }

    public MemberDto findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        // Entity -> Dto
        MemberDto memberDto = null;
        if(member != null) {
            memberDto = MemberDto.builder()
                    .userName(member.getUserName())
                    .email(member.getEmail())
                    .phoneNum(member.getPhoneNum())
                    .password(member.getPassword())
                    .createDate(member.getCreateDate())
                    .updateDate(member.getUpdateDate())
                    .flag(member.getFlag())
                    .build();
        }
        return memberDto;
    }

}
