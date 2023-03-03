package com.pidev.esprit.repository;

import com.pidev.esprit.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MenuRepository  extends JpaRepository<Menu, Long> {

    Menu findByName(String name);
    void deleteByName(String name);
    Menu findAllById(long id);

}
