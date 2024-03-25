package com.project.entity.business.helperentity;

import com.project.entity.enums.AdvertStatusType;
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
@Table(name = "t_adverts_status_role")
public class AdvertStatusRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private AdvertStatusType advertStatusType;

    private Integer advertStatusId;


}
