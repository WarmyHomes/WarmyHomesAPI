package com.project.entity.business.helperentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "advert_type",cascade = CascadeType.REMOVE)
    private List<Advert> advert;

}
