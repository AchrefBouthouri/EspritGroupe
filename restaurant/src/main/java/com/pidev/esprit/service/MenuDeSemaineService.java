package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.MenuDesemaine;
import com.pidev.esprit.model.Rien;
import com.pidev.esprit.repository.MenuDeSemaineRepository;
import com.pidev.esprit.repository.RienRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Service
@Slf4j
public class MenuDeSemaineService {
    @Autowired
    MenuDeSemaineRepository menuDeSemaineRepository;
    @Autowired
    RienRepository rienRepository;

    public String MenuD() {
        List<MenuDesemaine> menuDesemaines = menuDeSemaineRepository.findAll();
        long id1 = menuDesemaines.get(0).getId();
        MenuDesemaine menuDesemaine = menuDeSemaineRepository.findById(id1).get();
        String s = "";
        List<Menu> menus = menuDesemaine.getMenuList();
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(java.time.DayOfWeek.MONDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");


        for (int i = 0; i < 6; i++) {
            s = s + "\n" + "Le dejener de" + "  " + startOfWeek.plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + "  " + menus.get(i).getName();
            s = s + "\n" + "Le dinner de" + "  " + startOfWeek.plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + "  " + menus.get(6 + i).getName();
            startOfWeek.plusDays(i).format(formatter);

        }
        String data = s;
        File file = new File("C:/Users/user/Desktop/Menu.txt"); // the path to the file
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rienRepository.deleteAll();
        Rien rien = new Rien();
        rien.setS(s);
        rienRepository.save(rien);


        return s;


    }



}

