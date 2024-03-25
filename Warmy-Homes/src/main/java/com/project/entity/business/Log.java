package com.project.entity.business;


import com.project.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_log")
public class Log {
    //TugbaAkdogan

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String log;
    //CREATED Advert is created and wait for approve
    //UPDATED Advert is updated
    //DELETED Advert is deleted
    //DECLINED Advert is declined by manager
    //TOUR_REQUEST_CREATED Tour request is created
    //TOUR_REQUEST_ACCEPTED Tour request is accepted
    //TOUR_REQUEST_DECLINED Tour request is declined
    //TOUR_REQUEST_CANCELED Tour request is canceled

    @ManyToOne
    private User user;

    @ManyToOne
    private Advert advert;

    @NotNull
    private LocalDateTime create_at;
}
