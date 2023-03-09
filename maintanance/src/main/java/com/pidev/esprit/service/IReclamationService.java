package com.pidev.esprit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.model.dto.User;

import java.util.Collection;
import java.util.List;

public interface IReclamationService {
    List<Reclamation> getAllReclamations();
    Reclamation getReclamationById(long id);
    Reclamation createReclamation(Reclamation reclamation, long id) throws JsonProcessingException;
    Reclamation updateReclamation(Reclamation reclamation);
    void deleteReclamation(long id);
    Reclamation clotureReclamation(long idReclamation);
    RDV assignReclamation(Reclamation reclamation) throws JsonProcessingException;
    boolean checkTechnicianAvailability(Long idT);
    User getUserById(Long id);
    List<User> getAllUsers() throws JsonProcessingException;

}
