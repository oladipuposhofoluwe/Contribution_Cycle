package com.contribution.service.member;

import com.contribution.entity.Member;

public interface MemberService {
     Member registerNewMember(Member member);

    void initAdminRoleAndMemberRole();

    Member getUserByFirstName(String firstName) throws IllegalAccessException;
}
