package com.project.payload.response.user;

import com.project.entity.business.Advert;
import com.project.entity.business.Favorite;
import com.project.entity.business.Log;
import com.project.entity.business.Tour_Request;
import com.project.entity.user.UserRole;
import com.project.payload.request.abstracts.AbstractUserRequest;
import com.project.payload.response.abstracts.BaseUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAllFieldsResponse extends BaseUserResponse {
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

    @OneToMany(mappedBy = "owner_user_id", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tourRequests;

    @OneToMany(mappedBy = "guest_user_id", cascade = CascadeType.REMOVE)
    private List<Tour_Request> tour_requestList;

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.REMOVE)
    private List<Log> logs;

    @OneToMany(mappedBy = "user")
    private List<Advert> advertList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Favorite> favoriteList;
}
