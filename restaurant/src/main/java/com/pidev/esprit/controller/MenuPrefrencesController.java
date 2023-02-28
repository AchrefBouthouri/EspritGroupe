package com.pidev.esprit.controller;

import com.pidev.esprit.model.MenuPreferences;
import com.pidev.esprit.service.MenuPreferencesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MenuPreferences")
public class MenuPrefrencesController {
    @Autowired
MenuPreferencesServices menuPreferencesServices;


}
