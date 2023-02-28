package com.pidev.esprit.controller;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/restaurant")
public class MenuController {

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

}
