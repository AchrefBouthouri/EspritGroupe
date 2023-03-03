package com.pidev.esprit.controller;

import com.pidev.esprit.model.Repas;
import com.pidev.esprit.service.RepasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Repas")
public class RepasController {
    @Autowired
    RepasService repasService ;
    /*ggg*/
    @PostMapping ("/AjouterRepas")
    public Repas ajouterRepas(@RequestBody Repas repas){repasService.CreeRepas(repas);
    return repas;}
    @PutMapping("AffecterRepasMenu/{idRepas}/{idMenu}")
    public void affecterRepasMenu(@RequestParam long idMenu , @RequestParam long idRepas){
        repasService.AjouterMenuRepas(idRepas,idMenu);

    }
    @GetMapping("/afiicher")
    public List<Repas> afficher(){
        return repasService.aficherRepas();
    }
    @PutMapping("ModifierRepas/{id}")
    public Repas Modifierrepas(@RequestBody Repas repas , @RequestParam long idRepas){
        repasService.ModifierRepas(repas,idRepas);
        return repas;
    }
    @DeleteMapping("supprimerRepas/{id}")
    public void supprimerRepas(@RequestParam long idRepas){

        repasService.DeleteRepas(idRepas);

    }


}
