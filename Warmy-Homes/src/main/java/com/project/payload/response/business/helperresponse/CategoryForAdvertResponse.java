package com.project.payload.response.business.helperresponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryForAdvertResponse {

    private String category;
    private Integer amount;
}
