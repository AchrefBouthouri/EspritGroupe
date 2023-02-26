package com.pidev.esprit.repository;

import com.pidev.esprit.model.MenuPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
@Repository
public interface MenuPreferencesRepository extends JpaRepository<MenuPreferences,Long> {
}
