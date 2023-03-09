package com.pidev.esprit.service;

import com.google.zxing.WriterException;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.MenuDejour;
import com.pidev.esprit.model.MenuDesemaine;
import com.pidev.esprit.model.Order;
import com.pidev.esprit.repository.MenuDeJourRepository;
import com.pidev.esprit.repository.MenuDeSemaineRepository;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class MenuDeJourService {
    @Autowired
    MenuDeJourRepository menuDeJourRepository;
    MenuDeSemaineRepository menuDeSemaineRepository;

    @Scheduled(fixedRate = 20000000)
    public void GenererMenuDjour() {



    }


}