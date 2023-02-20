package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired

    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu menu) {
        Menu existingMenu = getMenuById(id);
        existingMenu.setName(menu.getName());
        existingMenu.setDescription(menu.getDescription());
        existingMenu.setPrice(menu.getPrice());
        existingMenu.setAvailable(menu.getAvailable());
        existingMenu.setCategory(menu.getCategory());
        return menuRepository.save(existingMenu);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}