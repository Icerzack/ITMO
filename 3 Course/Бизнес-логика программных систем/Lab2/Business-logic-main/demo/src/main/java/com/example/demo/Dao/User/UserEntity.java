package com.example.demo.Dao.User;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true,name="name")
    private String name;
    @Column(nullable = true,name="email")
    private String email;
    @Column(nullable = false,name="phone_number")
    private String phoneNumber;
    @Column(nullable = false,name="password")
    private String password;
    @Column(nullable = false,name="role")
    private String role;
}
