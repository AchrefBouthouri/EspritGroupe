package com.pidev.esprit.repository;

import com.pidev.esprit.model.Repas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepasRepository extends JpaRepository<Repas, Long> {
    Repas findById(long id);
}
