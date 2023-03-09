package com.pidev.esprit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pidev.esprit.model.dto.User;
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
public class Reclamation  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date confirmedAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String progress;
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    private Boolean availablity;
    private Long createdBy;


}
