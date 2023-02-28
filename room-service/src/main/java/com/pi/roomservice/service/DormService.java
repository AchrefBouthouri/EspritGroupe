package com.pi.roomservice.service;

import com.pi.roomservice.dto.DormRequest;
import com.pi.roomservice.dto.DormResponse;
import com.pi.roomservice.model.Room;
import com.pi.roomservice.repository.DormRepository;
import com.pi.roomservice.repository.RoomRepository;
import com.pi.roomservice.model.Dorm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DormService {
    private final DormRepository dormRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public void createDorm(DormRequest dormRequest) {
        Dorm dorm = Dorm.builder()
                .name(dormRequest.getName())
                .address(dormRequest.getAddress())
                .rooms(dormRequest.getRooms() == null || dormRequest.getRooms().isEmpty() ?
                        null : dormRequest.getRooms().stream().map(roomRequest -> roomService.createRoom(roomRequest))
                        .collect(Collectors.toList()))
                .build();
        dormRepository.save(dorm);

        log.info("Dorm {} is saved", dorm.getId());
    }
    public List<DormResponse> getAllDorms() {
        List<Dorm> dorms = dormRepository.findAll();

        return dorms.stream().map(this::mapToDormResponse).toList();
    }
    public DormResponse getByName(String Name) {
        Dorm dorm = dormRepository.findByName(Name);

        if (dorm == null) {
            log.error("Room with number {} not found", Name);
            return null;
        } else {
            return mapToDormResponse(dorm);
        }

    }
    public void updateDorm(String id, DormRequest dormRequest) {
        Optional<Dorm> dormOptional = dormRepository.findById(id);
        if (dormOptional.isPresent()) {
            Dorm dorm = dormOptional.get();
            dorm.setName(dormRequest.getName());
            dorm.setAddress(dormRequest.getAddress());
            dorm.setRooms(dormRequest.getRooms().stream().map(roomService::createRoom).collect(Collectors.toList()));

            dormRepository.save(dorm);

            log.info("Dorm {} is updated", dorm.getName());
        } else {
            log.error("Dorm with id {} not found", id);
        }
    }
    public void deleteDorm(String id) {
        Optional<Dorm> dormOptional = dormRepository.findById(id);
        if (dormOptional.isPresent()) {
            Dorm dorm = dormOptional.get();
            dormRepository.delete(dorm);

            log.info("Dorm {} is deleted", dorm.getId());
        } else {
            log.error("Dorm with id {} not found", id);
        }
    }
    public  DormResponse mapToDormResponse(Dorm dorm) {
        return DormResponse.builder()
                .id(dorm.getId())
                .name(dorm.getName())
                .address(dorm.getAddress())
                .rooms(dorm.getRooms().stream().map(roomService::mapToRoomResponse).toList())
                .build();
    }
    public List<Room> getRoomsByDormName(String name) {
        Dorm dorm = dormRepository.findByName(name);

        if (dorm == null) {
            log.error("Dorm with name {} not found", name);
            return Collections.emptyList();
        }

        return dorm.getRooms();
    }
}