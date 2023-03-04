package com.pidev.esprit.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private int calories;
    @OneToMany( cascade = CascadeType.REMOVE)
    List<MenuPreferences>menuPreferences;
    @OneToMany(cascade = CascadeType.REMOVE)
    List<Repas>repas;



}
