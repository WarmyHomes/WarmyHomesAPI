package com.project.contactmessage.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest {


    @NotNull(message = "Please enter first name")
    @Size(min = 3,max = 30)
    private String first_name;

    @NotNull(message = "Please enter last name")
    @Size(min = 3,max = 30)
    private String last_name;

    @NotNull(message = "Please enter email")
    @Size(min = 3,max = 60)
    @Email
    private String email;

    private Integer status=0;

    @NotNull(message = "Please enter message")
    @Size(max = 300)
    private String message;




}
