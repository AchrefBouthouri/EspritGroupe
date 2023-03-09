package com.pidev.esprit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDesemaine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Temporal(TemporalType.DATE)
    Date CreatedAt;
    @OneToMany(fetch = FetchType.EAGER)
    List<Menu>menuList;

}
