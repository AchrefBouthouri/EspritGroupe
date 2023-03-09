package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.MenuPreferences;
import com.pidev.esprit.repository.MenuPreferencesRepository;
import com.pidev.esprit.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuPreferencesServices {
    @Autowired
    MenuPreferencesRepository mpr;
    @Autowired
    MenuRepository mr;
    public void AjouterPreferencesMenu(MenuPreferences mp , long id ){
        Menu m = mr.findAllById(id);
        mpr.save(mp);
        List<MenuPreferences> mps = (List<MenuPreferences>) m.getMenuPreferences();
        mp.setDateDcreation(new Date());
        mps.add(mp);
        m.setMenuPreferences(mps);
        mr.save(m);






    }


}
