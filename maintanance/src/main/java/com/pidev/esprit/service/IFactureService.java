package com.pidev.esprit.service;

import com.pidev.esprit.model.Facture;

import java.util.List;
import java.util.Optional;

public interface IFactureService {
    Facture getFactureById(long id);
    List<Facture> getAllFactures();
    Facture createFacture(Facture facture, long id);
    Facture updateFacture(Facture facture);
}
