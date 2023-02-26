package com.pi.roomservice.repository;

import com.pi.roomservice.model.Dorm;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DormRepository extends MongoRepository<Dorm, String> {
    Dorm findByName(String name);

}
