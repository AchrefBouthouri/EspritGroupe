package com.pidev.esprit.service;


import com.pidev.esprit.controller.RDVController;
import com.pidev.esprit.controller.ReclamationController;
import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Utilisateur;
import com.pidev.esprit.repository.RDVRepository;
import com.pidev.esprit.repository.ReclamationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RDVService implements IRDVService {

    private static final Logger logger = LogManager.getLogger(RDVController.class);
    @Autowired
    RDVRepository rdvRepository;
    @Autowired
    IReclamationService reclamationService;

    public List<RDV> getAllRDVs() {
        return rdvRepository.findAll();
    }

    public RDV getRDVById(long id) {
        Optional<RDV> optionalRDV = rdvRepository.findById(id);
        return optionalRDV.orElse(null);
    }
    public RDV createRDV(RDV rdv, long idR, long idT) {
        rdv.setDate(new Date());
        rdv.setAvailablity(true);
        rdv.setTechnicien_id(reclamationService.getUserById(idT).getId());
        rdv.setReclamation(reclamationService.getReclamationById(idR));
        return rdvRepository.save(rdv);
    }

    public RDV updateRDV(RDV rdv, long id) {
        rdv.setDate(new Date());
        rdv.setTechnicien_id(reclamationService.getUserById(id).getId());
        return rdvRepository.save(rdv);}

    public void deleteRDV(long id) {rdvRepository.deleteById(id);}

}
