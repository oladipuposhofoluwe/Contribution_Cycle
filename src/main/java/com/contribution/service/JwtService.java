package com.contribution.service;

import com.contribution.request.JwtRequest;
import com.contribution.response.JwtResponse;
import com.contribution.entity.Member;
import com.contribution.repository.MemberRepository;
import com.contribution.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager1;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String password = jwtRequest.getPassword();
        authenticate(userName, password);

        final UserDetails userDetails = loadUserByUsername(userName);
       String newGeneratedToken = jwtUtil.generateToken(userDetails);
       Member member = memberRepository.findById(userName).get();
       member.setMessage("Login successful");
       return new  JwtResponse(member,newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Member member = memberRepository.findById(userName).get();

        if (member != null) {
            return new User(
                    member.getEmail(),
                    member.getPassword(),
                    getAuthorities(member)
            );
        } else {
            throw new UsernameNotFoundException("username is not valid");
        }
    }

    private Set getAuthorities(Member member) {
        Set authorities = new HashSet<>();
        member.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(("Role_" + role.getRoleName())));
        });
        return authorities;
    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager1.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        } catch (DisabledException e) {
            throw new Exception("Member s disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from Member");
        }

    }
}
