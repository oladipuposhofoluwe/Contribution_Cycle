package com.contribution.service.member.memberimpl;

import com.contribution.entity.Member;
import com.contribution.entity.Role;
import com.contribution.exceptions.BlogapiException;
import com.contribution.repository.MemberRepository;
import com.contribution.repository.RoleRepository;
import com.contribution.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Member registerNewMember(Member member) {
        Optional<Member> isEmailAlreadyExistInDataBase = memberRepository.findById(member.getEmail());
        if (isEmailAlreadyExistInDataBase.isPresent()){
            throw new BlogapiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        Role role = roleRepository.findById("Member").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        member.setRole(roles);
        member.setDateJoined(new Date());
        member.setPassword(getEncodedPassword(member.getPassword()));
        member.setMessage("Member successfully registered");
        return memberRepository.save(member);
    }


    public void initAdminRoleAndMemberRole() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role memberRole = new Role();
        memberRole.setRoleName("Member");
        memberRole.setRoleDescription("Member role");
        roleRepository.save(memberRole);

        Member adminMember = new Member();
        Date today = new Date();
        adminMember.setFirstName("Admin");
        adminMember.setLastName("Admin");
        adminMember.setEmail("admin@gmail.com");
        adminMember.setPhoneNumber("08056452232");
        adminMember.setPassword(getEncodedPassword("12345"));
        adminMember.setDateJoined(today);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminMember.setRole(adminRoles);
        memberRepository.save(adminMember);
//
//        Member member = new Member();
//        member.setFirstName("emma");
//        member.setLastName("Fabian");
//        member.setEmail("dipola@gmail.com");
//        member.setPhoneNumber("08056452232");
//        member.setPassword(getEncodedPassword("12345"));
//        member.setUserName("emma123");
//        member.setDateJoined(today);
//        Set<Role> memberRoles = new HashSet<>();
//        memberRoles.add(memberRole);
//        member.setRole(memberRoles);
//        memberRepository.save(member);
    }

    @Override
    public Member getUserByFirstName(String firstName) throws IllegalAccessException {
        Member member = memberRepository.findById("email").get();
        if (member == null){
            throw new IllegalAccessException("member does not exist");
        }
        return member;
    }


    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
