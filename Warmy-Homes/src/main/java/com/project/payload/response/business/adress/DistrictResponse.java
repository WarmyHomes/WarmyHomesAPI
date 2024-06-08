package com.project.payload.response.business.adress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DistrictResponse {

    private Long id;

    private String name;

    private Long city_id;


}
