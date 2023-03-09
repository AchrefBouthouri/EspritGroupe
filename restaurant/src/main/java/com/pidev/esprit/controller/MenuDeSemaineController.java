package com.pidev.esprit.controller;

import com.pidev.esprit.model.*;
import com.pidev.esprit.repository.MenuDeSemaineRepository;
import com.pidev.esprit.repository.RienRepository;
import com.pidev.esprit.service.JavaEmailService;
import com.pidev.esprit.service.MenuDeSemaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
public class MenuDeSemaineController {

    @Autowired
    MenuDeSemaineService menuDeSemaineService;
    @Autowired
    JavaEmailService javaEmailService;
    @Autowired
    MenuDeSemaineRepository menuDeSemaineRepository;
    @Autowired
    RienRepository rienRepository;

@GetMapping("/ppp")
    public String GetMenus(){
       return menuDeSemaineService.MenuD();
   }
   @PostMapping("/email")
    public void sendMail(@RequestParam("email") String email){
       List<Rien>riens = rienRepository.findAll();
       long id = riens.get(0).getId();
       Rien rien = rienRepository.findById(id).get();
       String s = rien.getS();
       javaEmailService.sendEmail(email, "Le menu de cette  semaine ",s);




   }












}
