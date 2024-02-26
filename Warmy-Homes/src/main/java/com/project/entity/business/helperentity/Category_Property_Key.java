package com.project.entity.business.helperentity;

import com.project.entity.business.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_property_keys")
public class Category_Property_Key {
    //SongulCelik

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter name")
    @Size(min = 2, max = 80,message = "Name must be maximum 80 characters")
    private String name;

    //default olarak 0 degeri nasil verebilirim?
    private Boolean built_in;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "category_property_key_id", cascade = CascadeType.REMOVE)
    private List<Category_Property_Value> category_property_values;

}
