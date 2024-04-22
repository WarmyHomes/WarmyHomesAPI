package com.project.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequest {
    private String password_hash;
    private String retry_password_hash;

    private String reset_password_codee;
}
