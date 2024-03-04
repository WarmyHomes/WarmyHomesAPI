package com.project.payload.response.business;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Category_Property_Key_Response {
    private Long id;
    private String name;


}