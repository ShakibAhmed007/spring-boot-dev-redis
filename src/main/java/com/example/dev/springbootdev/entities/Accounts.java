package com.example.dev.springbootdev.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "user_email")
    @NotNull
    private String userEmail;

    @Column(name = "user_age")
    @NotNull
    private Integer userAge;

    @Column(name = "user_role")
    @NotNull
    private String userRole;

    @Column(name = "status_id")
    @NotNull
    private Integer statusId;

    @Column(name = "created_on")
    @NotNull
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @NotNull
    private LocalDateTime updatedOn;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
}
