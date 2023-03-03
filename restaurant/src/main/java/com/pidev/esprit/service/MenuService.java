package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
public class MenuService {

    @Autowired

    private MenuRepository menuRepository;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuByName(String name) {
        Menu M =menuRepository.findByName(name);
        if(M==null){
            throw new NotFoundException("Menu n'existe pas" + name);
        }

        return M;
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }
    public Menu find(String name) {
        Menu M;
        M = menuRepository.findByName(name);
        return M;
    }
    public Menu updateMenu(String name,Menu menu2) {
        Menu M ;
        M=menuRepository.findByName(name);

            M.setName(menu2.getName());
            M.setCategory(menu2.getCategory());
            M.setAvailable(menu2.getAvailable());
            M.setDescription(menu2.getDescription());
            M.setPrice(menu2.getPrice());
            menuRepository.save(M);
            return M;

        /*Menu existingMenu = getMenuById(id);
        existingMenu.setName(menu.getName());
        existingMenu.setDescription(menu.getDescription());
        existingMenu.setPrice(menu.getPrice());
        existingMenu.setAvailable(menu.getAvailable());
        existingMenu.setCategory(menu.getCategory());
        return menuRepository.save(existingMenu);*/

    }

    public void deleteMenu(String name) {
        menuRepository.deleteByName(name);
    }
    @Scheduled(fixedRate = 20000)
    public void GenererMenu() {
        List<Menu> menus = menuRepository.findAll();
        for (int i = 0; i < 12; i++) {
            Random random = new Random();
            int RI = random.nextInt(menus.size());
           Menu m = menus.get(i);
           log.info(m.getName());


    }


}

}