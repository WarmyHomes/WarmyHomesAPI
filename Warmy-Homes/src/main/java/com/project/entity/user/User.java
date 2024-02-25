package com.project.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String email;

    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Sadece DB'e kayit yaptik ve Response'da password DTO class'da olmasin diye yaptim
    private String password_hash;

    private String reset_password_code;

    private Boolean built_in;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    private LocalDateTime create_at;

    private LocalDateTime update_at;

    @OneToMany
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)//Sadece DB'ye kayit yaptik Response'da Role donmeyecek
    private UserRole userRole;
}
