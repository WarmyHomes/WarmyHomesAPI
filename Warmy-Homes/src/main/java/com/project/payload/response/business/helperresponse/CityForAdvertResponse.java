package com.project.payload.response.business.helperresponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CityForAdvertResponse {

    private String city;

    private Integer amount;
}
