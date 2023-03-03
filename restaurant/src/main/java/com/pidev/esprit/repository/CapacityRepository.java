package com.pidev.esprit.repository;

import com.pidev.esprit.model.Capacity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityRepository extends CrudRepository<Capacity, Long> {
}