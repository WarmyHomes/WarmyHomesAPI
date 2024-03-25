package com.project.entity.business.helperentity;

import com.project.entity.enums.StatusType;
import com.project.entity.enums.TourStatus;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "t_tour_status_role")
public class TourStatusRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TourStatus tourStatus;

    @NotNull
    private String statusName;


}
