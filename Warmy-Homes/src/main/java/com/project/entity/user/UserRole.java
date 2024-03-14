package com.project.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    @NotNull
    private String name;


   // @JsonProperty(access = JsonProperty.Access.READ_ONLY)

  // @ManyToMany(mappedBy = "userRoleList", fetch = FetchType.EAGER)
  // private List<User> userList ;
}
