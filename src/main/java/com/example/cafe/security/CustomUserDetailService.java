package com.example.cafe.security;

import com.example.cafe.Repository.Member.MemberRepository;
import com.example.cafe.dto.MemberDto;
import com.example.cafe.entity.Member;
import com.example.cafe.entity.MemberContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);

        if(member == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getFlag()));

        // Entity -> Dto
        ModelMapper modelMapper = new ModelMapper();
        MemberDto memberDto = modelMapper.map(member, MemberDto.class);

        return new MemberContext(memberDto, authorities);
    }
}
