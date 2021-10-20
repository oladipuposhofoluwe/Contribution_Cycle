package com.contribution.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Data
@Entity
public class Member {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

//    @Column(nullable = false)
//    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Date dateJoined;

    private String message;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(	name = "MEMBER_ROLE",
            joinColumns = {
             @JoinColumn(name = "MEMBER_ID")
            },

            inverseJoinColumns = {
                @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;
}
