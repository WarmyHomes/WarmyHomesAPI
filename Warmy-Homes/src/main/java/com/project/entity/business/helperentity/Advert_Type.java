package com.project.entity.business.helperentity;

import com.project.entity.business.Advert;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "t_advertType")
public class Advert_Type {
    //EnesBilgic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Column(nullable = false)
    private Boolean builtIn;

    @OneToMany
    private List<Advert> advert;

}
