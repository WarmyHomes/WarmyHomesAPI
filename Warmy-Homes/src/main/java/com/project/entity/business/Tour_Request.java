package com.project.entity.business;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.business.helperentity.StatusRole;
import com.project.entity.business.helperentity.TourStatusRole;
import com.project.entity.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_tour_request")
@Builder(toBuilder = true)
public class Tour_Request {
    //TugbaAkdogan

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You must enter tour date")
    private LocalDate tour_date;

    @NotNull(message = "You must enter tour time")
    private LocalTime tour_time;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TourStatusRole status;
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
