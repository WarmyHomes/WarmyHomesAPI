package com.project.payload.response.business;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PropertyKeyResponse {
    private Long id;
    private String name;


}