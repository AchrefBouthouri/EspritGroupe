package com.pidev.esprit.controller;

import com.pidev.esprit.model.Facture;
import com.pidev.esprit.service.IFactureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Bill")
@RestController
@RequestMapping("/api/maintenance/Facture")
public class FactureController {

    @Autowired
    IFactureService factureService;

    @GetMapping
    public List<Facture> getAll(){return factureService.getAllFactures();}

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable("id") long id) {
        Facture facture = factureService.getFactureById(id);
        if (facture != null) {
            return ResponseEntity.ok(facture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture, @PathVariable("id") long id) {
        Facture newFacture = factureService.createFacture(facture,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFacture);
    }

    @PutMapping
    public ResponseEntity<Facture> updateFacture(@RequestBody Facture facture) {
        Facture updatedFacture = factureService.updateFacture(facture);
        if (updatedFacture != null) {
            return ResponseEntity.ok(updatedFacture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
