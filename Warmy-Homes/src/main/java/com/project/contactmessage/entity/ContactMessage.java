package com.project.contactmessage.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder(toBuilder = true)
@Table(name = "t_contact_message")
public class ContactMessage {
    //Yunus_Emre_AKKAS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String email;

    @NotNull
    private String message;

    @NotNull
    private Integer status; //default 0 olmalı
    // status ; 0 => it is not opened by admins yet
    // status ; 1 => it was opened and read

    @NotNull
    private LocalDateTime date_time;

}
