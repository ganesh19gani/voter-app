package com.voter.app.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "voterList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vid;

    @NotEmpty(message = "VoterId should not be Empty")
    @NotNull(message = "VoterId Should not be null")
    @Column(unique = true)
    private String voterIdEpicNumber;

    @NotEmpty(message = "VoterName should not be Empty")
    @NotNull(message = "VoterName Should not be null")
    private String voterName;

    private String voterAddress;

    @NotEmpty(message = "Constituency should not be Empty")
    @NotNull(message = "Constituency Should not be null")
    private String constituency;


    private String voterBoothNumber;

    @NotEmpty(message = "VoterBoothName should not be Empty")
    @NotNull(message = "VoterBoothName Should not be null")
    private String voterBoothName;

    @NotEmpty(message = "voter Gender should not be Empty")
    @NotNull(message = "voter Gender Should not be null")
    private String voterGender;

    @NotEmpty(message = "Expected Vote should not be Empty")
    @NotNull(message = "Expected Vote Should not be null")
    private String expected;

    private String actual;


}
