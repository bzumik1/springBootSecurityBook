package com.znamenacek.jakub.spring_boot_security_book.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable( //This is only possibility for ManyToMany association
            name="user_role",
            joinColumns = {@JoinColumn(name = "User_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="Role_ID", referencedColumnName = "ID")})
    private List<Role> roles;

}
