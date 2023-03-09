package com.pidev.esprit.repository;

import com.pidev.esprit.model.Rien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RienRepository extends JpaRepository<Rien,Long> {
}
