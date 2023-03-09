package com.pidev.esprit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RDV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Boolean availablity;
    @OneToOne
    @JsonIgnore
    private Reclamation reclamation;
    private Long technicien_id;

}
