package com.project.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseUserRequest extends AbstractUserRequest {
    @NotNull(message = "Please enter your password")
    @Size(min = 8, max = 60,message = "Your password should be at least 8 chars or maximum 60 characters")
    //password syntax validation could be a good feature.
   // @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[-+_!@#$%^&*., ?]).+$")
    private String password_hash;
}
