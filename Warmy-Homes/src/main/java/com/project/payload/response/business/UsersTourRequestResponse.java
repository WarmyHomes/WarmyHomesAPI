package com.project.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.entity.business.Advert;
import com.project.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersTourRequestResponse {

    private Long id;

    private LocalDate tour_date;

    private LocalTime tour_time;

    private String status;

    private LocalDateTime create_at;

    private LocalDateTime update_at;

    private Advert advert;

    private User ownerUser;

    private User guestUser;


}
