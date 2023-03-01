package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Repas;
import com.pidev.esprit.repository.MenuRepository;
import com.pidev.esprit.repository.RepasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class RepasService {
    @Autowired
    RepasRepository rp;
    @Autowired
    MenuRepository Mr;
    public Repas CreeRepas(Repas r){  rp.save(r);
    return r;}
    public void TrouverRepas(long id){ rp.findById(id);}
   /* public Repas ModifierRepas(long id,Repas r){
        Repas repas;
        repas = rp.findById(id);
        repas.setType(r.getType());
        repas.setName(r.getName());
        rp.save(repas);
        return repas;

    }*/
    public List<Repas> aficherRepas(){
        return rp.findAll();

    }

    public void AjouterMenuRepas(long idRepas , long idMenu){
        Menu m = Mr.findAllById(idMenu);
        Repas r = rp.findById(idRepas);
        List<Repas>repas = m.getRepas();
        repas.add(r);
        m.setRepas(repas);
        Mr.save(m);
    }
 public Repas ModifierRepas(Repas repas , long idRepas){

        Repas r = rp.findById(idRepas);
        if (r==null){
        r.setName(repas.getName());
        r.setType(repas.getType());
        rp.save(r);}else {
            throw new NotFoundException("Repas n'existe pas");

        }

        return  r;
 }
 public void DeleteRepas(long idRepas){
        if(rp.existsById(idRepas)){
        rp.deleteById(idRepas);}else {
            throw new NotFoundException("Repas n'existe pas");

        }

 }
}
