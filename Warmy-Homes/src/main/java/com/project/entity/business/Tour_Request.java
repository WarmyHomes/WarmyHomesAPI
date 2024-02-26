package com.project.entity.business;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "t_tour_request")
public class Tour_Request {
    //TugbaAkdogan

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You must enter tour date")
    private LocalDateTime tour_date;

    @NotNull(message = "You must enter tour time")
    private LocalDateTime tour_time;

    @NotNull
    private Integer status;
    //status:
    //0 Pending Initial value
    //1 Approved Can be approved by owner of property
    //2 Declined Can be declined by owner of property
    //3 Canceled Can be canceled by owner of tour request


    @ManyToOne
    @JoinColumn(name ="advert_id", nullable = false)
    private Advert advert_id;

    @ManyToOne
    @JoinColumn(name="owner_user_id", nullable = false)
    private User owner_user_id;

    @ManyToOne
    @JoinColumn(name = "guest_user_id", nullable = false)
    private User guest_user_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    private LocalDateTime create_at;

    @Column(nullable = true)
    private LocalDateTime update_at;


}
