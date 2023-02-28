package com.pi.roomservice.repository;

import com.pi.roomservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Room findByRoomNumber(String roomNumber);
}
