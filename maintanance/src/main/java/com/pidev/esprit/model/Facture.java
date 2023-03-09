package com.pidev.esprit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

//@Document(value = "Facture")
@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ref;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateFacture;
    private double total;
    private double ttc;
    @OneToOne
    @JsonIgnore
    private RDV rdv;


}

