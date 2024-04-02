package com.project.payload.request.business.tourRequestRequests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.entity.business.Advert;
import com.project.entity.business.helperentity.TourStatusRole;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TourRequestRequest {


    @NotNull(message = "Tour Date must not be empty")
    private LocalDate tour_date;

    @NotNull(message = "Tour Time must not be empty")
    private LocalTime tour_time;

    @NotNull(message = "Tour Status must not be empty")
    private String status;

    @NotNull(message = "Create date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime create_at;

    @Null
    private LocalDateTime update_at;

    @NotNull
    private Long advert_id;



}
