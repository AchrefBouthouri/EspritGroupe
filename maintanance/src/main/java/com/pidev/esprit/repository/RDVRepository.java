package com.pidev.esprit.repository;

import com.pidev.esprit.model.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RDVRepository extends JpaRepository<RDV,Long> {
}
