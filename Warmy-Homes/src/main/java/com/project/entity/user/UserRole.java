package com.project.entity.user;

import com.project.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRole {

    //iliskiler belirlenecek
    private User user_id;

    //iliskiler belirlenecek
    private Role role_id;

    @Enumerated(EnumType.STRING)
    private Role roles;
}
