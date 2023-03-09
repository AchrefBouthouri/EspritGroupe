package com.pidev.esprit.controller;

import com.pidev.esprit.model.RDV;
import com.pidev.esprit.model.Reclamation;
import com.pidev.esprit.model.Utilisateur;
import com.pidev.esprit.service.IRDVService;
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

@Tag(name="Appointment")
@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenance/rdv")
@Slf4j
public class RDVController {

    private static final Logger logger = LogManager.getLogger(RDVController.class);

    @Autowired
    private IRDVService rdvService;


    @GetMapping
    public List<RDV> getAllRDVs() {return rdvService.getAllRDVs();}

    @GetMapping("/{id}")
    public ResponseEntity<RDV> getRDVById(@PathVariable("id") long id) {
        RDV rdv = rdvService.getRDVById(id);
        if (rdv != null) {
            return ResponseEntity.ok(rdv);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idReclamation}/{idTechnician}")
    public ResponseEntity<RDV> createRDV(@RequestBody RDV rdv, @PathVariable("idReclamation") long idR, @PathVariable("idTechnician") long idT) {
        RDV newRDV = rdvService.createRDV(rdv,idR,idT);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRDV);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RDV> updateRDV(@RequestBody RDV rdv, @PathVariable("id") long id) {
        RDV updatedRDV = rdvService.updateRDV(rdv,id);
        if (updatedRDV != null) {
            return ResponseEntity.ok(updatedRDV);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRDV(@PathVariable("id") long id) {
        rdvService.deleteRDV(id);
        return ResponseEntity.noContent().build();
    }
}
