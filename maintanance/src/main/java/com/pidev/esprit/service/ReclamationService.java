package com.pidev.esprit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pidev.esprit.controller.ReclamationController;
import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.model.dto.*;
import com.pidev.esprit.repository.RDVRepository;
import com.pidev.esprit.repository.ReclamationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ReclamationService implements IReclamationService {
    @Autowired
    private RDVRepository rdvRepository;

    //private static final Logger logger = LogManager.getLogger(ReclamationController.class);
    @Autowired
    private final WebClient.Builder webClientBuilder;

    @Autowired
    private ReclamationRepository reclamationRepository;

    public ReclamationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Reclamation getReclamationById(long id) {
        Optional<Reclamation> optionalReclamation = reclamationRepository.findById(id);
        return optionalReclamation.orElse(null);
    }

    public Reclamation createReclamation(Reclamation reclamation, long id) throws JsonProcessingException {
        reclamation.setCreatedAt(new Date());
        reclamation.setAvailablity(true);
        reclamation.setCreatedBy(id);
        reclamationRepository.save(reclamation);
        RDV newRdv =  assignReclamation(reclamation);
        if(newRdv == null){
            System.out.println("Pas De Technicien Disponible");
        }
        return reclamation;
    }

    public Reclamation updateReclamation(Reclamation reclamation) {
        reclamation.setUpdatedAt(new Date());
        return reclamationRepository.save(reclamation);
    }

    public void deleteReclamation(long id) {
        reclamationRepository.deleteById(id);
    }


    public Reclamation clotureReclamation(long idReclamation) {
        Reclamation reclamation = reclamationRepository.findById(idReclamation).get();
        reclamation.setAvailablity(false);
        return reclamationRepository.save(reclamation);
    }

    public RDV assignReclamation(Reclamation reclamation) throws JsonProcessingException {
        List<User> userList = getAllUsers();
        for (User user:userList) {
            if(checkTechnicianAvailability(user.getId())){
                RDV newRdv = new RDV();
                newRdv.setReclamation(reclamation);
                newRdv.setTechnicien_id(user.getId());
                newRdv.setDescription(reclamation.getDescription());
                newRdv.setDate(new Date());
                newRdv.setAvailablity(true);
                return rdvRepository.save(newRdv);
            }
        }
        return null;
    }

    public boolean checkTechnicianAvailability(Long id) {
        boolean check = true;
        List<RDV> rdvList = rdvRepository.findAll();
        for (RDV rdv:rdvList) {
            if (rdv.getDate().compareTo(new Date()) > 0){
                if(rdv.getTechnicien_id() == id) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public User getUserById(Long id) {
        String url = "http://user-service/api/user/{id}";
        User utilisateur = webClientBuilder.build()
                .get()
                .uri(url, id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        if (utilisateur == null) {
            throw new RuntimeException("User with id="+ id +" not found");
        }
        return utilisateur;
    }

    public List<User> getAllUsers() throws JsonProcessingException {
        String url = "http://user-service/api/users";
        String response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null) {
            throw new RuntimeException("Users not found");
        }
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(response, new TypeReference<List<User>>(){});
        return users;
    }
}
