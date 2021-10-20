package com.contribution.controller;

import com.contribution.request.JwtRequest;
import com.contribution.response.JwtResponse;
import com.contribution.service.JwtService;
import com.contribution.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtService jwtService;

    @PostConstruct
    public void initAdminRoleAndMemberRoles(){
        memberService.initAdminRoleAndMemberRole();
    }

    @GetMapping("/unprotected")
    public String route(){
        return "Its working";
    }

    @PostMapping("/Newlogin")
    public String login() throws Exception {
        return "login";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    @GetMapping("/restricted")
    public String restricted(){
        return "google sign on is working";
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public String forAdmin(){
        return "This URL is accessible for Admin";
    }
}
