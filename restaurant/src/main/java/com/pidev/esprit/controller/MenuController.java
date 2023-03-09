package com.pidev.esprit.controller;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.model.Repas;
import com.pidev.esprit.repository.MenuRepository;
import com.pidev.esprit.service.MenuService;
import lombok.AllArgsConstructor;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.ws.rs.PathParam;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/restaurant")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @GetMapping("/findmenu/{nom}")
    public Menu trouverMenu(@PathParam("nom") String nom){
        return menuService.find(nom);

    }

    @GetMapping("/getmenus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{name}")
    public Menu getMenuByName(@PathVariable String name) {
        return menuService.getMenuByName(name);
    }

    @PostMapping("/creemenu")
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping("modifiermenu/{nom}")
    public Menu updateMenu(@PathVariable("nom") String nom, @RequestBody Menu menu) {
        return menuService.updateMenu(nom, menu);

    }
    @DeleteMapping("/{name}")
    public void deleteMenu(@PathVariable String name) {
        menuService.deleteMenu(name);
    }
    @GetMapping("/qr")
    public ResponseEntity<byte[]> GnererQr(@RequestParam("nom") String nom){
        try{
            Menu menu = menuService.getMenuByName(nom);
            List<Repas>repasLis = menu.getRepas();
            String s ="";
            for(int i=0 ; i<3; i++){
                s= s +" "+repasLis.get(i).getName();


            }
            ByteArrayOutputStream out = QRCode.from(s).to(ImageType.PNG).stream();
            byte[] qrCodeBytes = out.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(qrCodeBytes.length);

            return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);


        }catch (Exception e){
            throw new NotFoundException("Menu introuvabe");

        }



    }


}
