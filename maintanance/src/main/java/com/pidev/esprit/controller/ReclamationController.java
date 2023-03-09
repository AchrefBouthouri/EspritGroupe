package com.pidev.esprit.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.model.Utilisateur;
import com.pidev.esprit.service.IReclamationService;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.logging.log4j.Logger;


@Tag(name="Maintenance")
@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenance/reclamation")
@Slf4j
public class ReclamationController {

        private static final Logger logger = LogManager.getLogger(ReclamationController.class);

        @Autowired
        private IReclamationService reclamationService;

        @GetMapping
        //@ApiOperation(value = "Get My Resource by ID", nickname = "getMyResourceById")
        public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

        @GetMapping("/{id}")
        public ResponseEntity<Reclamation> getReclamationById(@PathVariable("id") long id) {
            Reclamation reclamation = reclamationService.getReclamationById(id);
            //logger.trace("optionalReclamation: "+reclamation);
            if (reclamation != null) {
                return ResponseEntity.ok(reclamation);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping("/{id}")
        public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation,@PathVariable("id") long id) throws JsonProcessingException {
            Reclamation newReclamation = reclamationService.createReclamation(reclamation,id);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReclamation);
        }

        @PutMapping
        public ResponseEntity<Reclamation> updateReclamation(@RequestBody Reclamation reclamation) {
            Reclamation updatedReclamation = reclamationService.updateReclamation(reclamation);
            if (updatedReclamation != null) {
                return ResponseEntity.ok(updatedReclamation);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReclamation(@PathVariable("id") long id) {
            reclamationService.deleteReclamation(id);
            return ResponseEntity.noContent().build();
        }

        /*
        @PutMapping("assign/{id}")
        public ResponseEntity<Reclamation> assignReclamationToTechnician(@PathVariable("id") long id, @RequestBody Utilisateur technician) {
            Reclamation assignReclamation = reclamationService.assignReclamationToTechnician(technician, id);
            if (assignReclamation != null) {
                logger.info("assignReclamation: "+assignReclamation);
                return ResponseEntity.ok(assignReclamation);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        */

        @PutMapping("cloture/{id}")
        public ResponseEntity<Reclamation> clotureReclamation(@PathVariable("id") long id) {
            Reclamation clotureReclamation = reclamationService.clotureReclamation(id);
            if (clotureReclamation != null) {
                return ResponseEntity.ok(clotureReclamation);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

}
