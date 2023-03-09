package com.pidev.esprit.repository;

import com.pidev.esprit.model.MenuDejour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MenuDeJourRepository extends JpaRepository<MenuDejour,Long> {
}
