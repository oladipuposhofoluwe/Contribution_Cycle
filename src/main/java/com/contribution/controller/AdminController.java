package com.contribution.controller;

import com.contribution.entity.Member;
import com.contribution.service.JwtService;
import com.contribution.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

    @Autowired
    JwtService jwtService;


    @Autowired
    private MemberService memberService;

    @GetMapping("/users/{firstName}")
    public Member getUserByFirstName(@PathVariable (name = "firstName") String firstName) throws IllegalAccessException {
        return memberService.getUserByFirstName(firstName);
    }

    @PostMapping("/registerNewMember")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public Member registerNewMember(@RequestBody Member member){
        return memberService.registerNewMember(member);
    }
}
