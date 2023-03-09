package com.pidev.esprit.service;

import com.pidev.esprit.model.Facture;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.repository.FactureRepository;
import com.pidev.esprit.repository.RDVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FactureService implements IFactureService{

    @Autowired
    FactureRepository factureRepository;
    @Autowired
    RDVRepository rdvRepository;

    public Facture getFactureById(long id) {
        Optional<Facture> optionalFacture = factureRepository.findById(id);
        return optionalFacture.orElse(null);
    }

    public List<Facture> getAllFactures(){return factureRepository.findAll();}

    public Facture createFacture(Facture facture, long id) {
        facture.setRdv(rdvRepository.getReferenceById(id));
        return factureRepository.save(facture);
    }

    public Facture updateFacture(Facture facture) {
        Optional<Facture> factureOptional = factureRepository.findById(facture.getRef());
        if (factureOptional.isPresent()) {
            return factureRepository.save(facture);
        } else {
            return facture;
        }
    }
}
