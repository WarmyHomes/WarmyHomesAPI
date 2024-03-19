package com.project.entity.business;

import com.project.entity.enums.ImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
//TODO class ismini duzelt
public class Image {
    //EmreAktas

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob //büyük boyutlu datalar için sütun oluşmasını sağlar
    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private String name;


    //@Column
    //private String type;
    //todo emun tipinde olsa daha mi iyi olur?
    @Enumerated(EnumType.STRING) // Enum olarak saklanacak
    @Column(nullable = false)
    private ImageType type;

    @Column(nullable = false)
    private Boolean featured;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = true) // nullable = true olarak ayarlandı
    private Advert advert_id;


}




