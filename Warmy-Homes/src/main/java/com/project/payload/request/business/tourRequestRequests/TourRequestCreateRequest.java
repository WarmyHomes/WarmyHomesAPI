package com.project.payload.request.business.tourRequestRequests;

import com.project.entity.business.Advert;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TourRequestCreateRequest {

    @NotNull(message = "Tour Date must not be empty")
    private LocalDate tour_date;

    @NotNull(message = "Tour Time must not be empty")
    private LocalTime tour_time;

    @NotNull
    private Long advert_id;
}
