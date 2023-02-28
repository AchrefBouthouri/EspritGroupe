package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.MenuPreferences;
import com.pidev.esprit.repository.MenuPreferencesRepository;
import com.pidev.esprit.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuPreferencesServices {
    @Autowired
    MenuPreferencesRepository mpr;
    @Autowired
    MenuRepository mr;

}
