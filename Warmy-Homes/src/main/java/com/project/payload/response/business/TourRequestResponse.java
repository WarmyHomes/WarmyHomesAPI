package com.project.payload.response.business;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TourRequestResponse {

    private Long id;

    private LocalDate tour_date;

    private LocalTime tour_time;

    private Integer status;

    private LocalDateTime create_at;

    private LocalDateTime update_at;

}
