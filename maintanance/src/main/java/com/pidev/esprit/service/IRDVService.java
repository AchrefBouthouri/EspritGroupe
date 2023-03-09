package com.pidev.esprit.service;

import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Reclamation;

import java.util.List;

public interface IRDVService {

    List<RDV> getAllRDVs();

    RDV getRDVById(long id);

    RDV createRDV(RDV rdv, long idR, long idT);

    RDV updateRDV(RDV rdv, long id);

    void deleteRDV(long id);
}
