package com.voter.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="parties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @NotEmpty(message = "Party Name should not be Empty")
    @NotNull(message = "Party Name Should not be null")
    @Column(unique = true)
    private String pname;
    @NotEmpty(message = "Party Logo should not be Empty")
    @NotNull(message = "Party Logo Should not be null")
    @Lob
    private String plogo;

    @NotEmpty(message = "Party Full Name should not be Empty")
    @NotNull(message = "Party Full Name Should not be null")
    @Column(unique = true)
    private String pabv;



}
