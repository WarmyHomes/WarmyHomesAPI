package com.project.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class AbstractUserRequest {

    @NotNull(message = "Please enter your name")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters .")
    private String first_name;

    @NotNull(message = "Please enter your last name")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters .")
    private String last_name;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;

    @NotNull(message = "Please enter your phone number")
    @Size(min = 10, max = 10,message = "Your phone number should be 10 characters long")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phone;


    private UserRole userRole;
    private Boolean built_in;









}
