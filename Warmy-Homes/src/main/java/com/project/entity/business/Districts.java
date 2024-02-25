package com.project.entity.business;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class Districts {
    //EmreAktas


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;


    //TODO class ismini duzelt
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private Cities city;


}


