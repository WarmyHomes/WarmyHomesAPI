package com.project.entity.business;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //oneToMany
    private long userId;

    //oneToMany
    private long advertId;

    private Date createAt;


}
