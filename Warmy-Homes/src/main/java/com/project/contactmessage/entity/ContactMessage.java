package com.project.contactmessage.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder(toBuilder = true)
public class ContactMessage {
    //Yunus_Emre_AKKAS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(30)
    private String first_name;

    @NotNull
    @Max(30)
    private String last_name;

    @NotNull
    @Max(60)
    private String email;

    @NotNull
    @Max(300)
    private String message;

    @NotNull
    private Integer status; //default 0 olmalı
    // status ; 0 => it is not opened by admins yet
    // status ; 1 => it was opened and read

    @NotNull
    private LocalDateTime date_time;

}
