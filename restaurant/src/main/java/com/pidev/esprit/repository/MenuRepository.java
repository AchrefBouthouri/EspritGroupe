package com.pidev.esprit.repository;

import com.pidev.esprit.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository  extends JpaRepository<Menu, Long> {
}
