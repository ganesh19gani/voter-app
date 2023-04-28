package com.voter.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty(message = "User Name should not be Empty")
    @NotNull(message = "User Name Should not be null")
    private String userName;

    @NotEmpty(message = "User Role should not be Empty")
    @NotNull(message = "User Role  Should not be null")
    private String userRole;

    @NotEmpty(message = "Email should not be Empty")
    @NotNull(message = "Email Should not be null")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Constituency should not be Empty")
    @NotNull(message = "Constituency Should not be null")
    private String constituency;

    private String userBoothNumber;

    @NotEmpty(message = "User Booth Name should not be Empty")
    @NotNull(message = "User Booth Name Should not be null")
    private String userBoothName;



    private String userPhoneNumber;

    private String userPassword;

    @NotEmpty(message = "UserAffiliatedParty Name should not be Empty")
    @NotNull(message = "UserAffiliatedParty Name Should not be null")
    private String userAffiliatedParty;

}
