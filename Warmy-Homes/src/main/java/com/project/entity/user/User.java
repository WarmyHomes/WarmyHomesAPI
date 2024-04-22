package com.project.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.abstracts.EntryDate;
import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import com.project.entity.business.Log;
import com.project.entity.business.Tour_Request;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.project.entity.enums.RoleType;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name="t_user")
public class User extends EntryDate {

    @Column(name = "create_at")
    private LocalDateTime create_at;

    @Nullable

    private LocalDateTime update_at;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name must not be empty! ")
    @Size(min=2 , max =30 , message= "First name should be at least 2 characters!")
    private String first_name;

    @NotNull(message = "Last name must not be empty! ")
    @Size(min=2 , max =30 , message= "Last name should be at least 2 characters!")
    private String last_name;

    @NotNull(message = "Email must not be empty!")
    @Email(message = "Please, enter valid email!")
    @Size(min=10, max=80 , message = "Your email should be between 10 and 80 chars!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Phone number must not be empty!")
    private String phone;


    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain at least one letter"),
            @Pattern(regexp = ".*[!@#$%^&*()-_=+[{]}|;:',<.>/?].*", message = "Password must contain at least one special character")
    })
    private String password_hash;

    @Nullable
    private String reset_password_code;

    private Boolean built_in;



    @OneToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;


    @OneToMany(mappedBy = "owner_user", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tourRequests;

    @OneToMany(mappedBy = "guest_user", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tour_requestList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Log> logs;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Advert> advertList;



    @PrePersist
    private void resetPasswordCode(){
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = upperCaseLetters.toLowerCase();
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_=+[{]}|;:',<.>/?";

        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + symbols;

        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(combinedChars.length());
            passwordBuilder.append(combinedChars.charAt(randomIndex));
        }

        reset_password_code=passwordBuilder.toString();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Favorite> favoriteList;



}
