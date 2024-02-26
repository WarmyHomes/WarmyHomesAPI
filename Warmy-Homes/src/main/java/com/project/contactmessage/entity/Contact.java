package com.project.contactmessage.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder(toBuilder = true)
public class Contact {
    //Yunus_Emre_AKKAS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Max(30)
    private String firstName;

    @NotNull
    @Max(30)
    private String lastName;

    @NotNull
    @Max(60)
    private String email;

    @NotNull
    @Max(300)
    private String message;

    @NotNull
    private int status;
    // status ; 0 => it is not opened by admins yet
    // status ; 1 => it was opened and read

    @NotNull
    private LocalDateTime dateTime;

}
