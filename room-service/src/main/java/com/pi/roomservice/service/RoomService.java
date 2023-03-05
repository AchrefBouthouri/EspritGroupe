package com.pi.roomservice.service;

import com.pi.roomservice.dto.RoomAvailability;
import com.pi.roomservice.dto.RoomRequest;
import com.pi.roomservice.dto.RoomResponse;
import com.pi.roomservice.model.Dorm;
import com.pi.roomservice.repository.DormRepository;
import com.pi.roomservice.repository.RoomRepository;
import com.pi.roomservice.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoomService {
    private static final String AVAILABILITY_SERVICE_URL = "http://availability-service/api/availability";

    private final WebClient.Builder webClientBuilder;
    private final RoomRepository roomRepository;
    private final DormRepository dormRepository;

    public Room createRoomInDorm(String name, RoomRequest roomRequest) {
        Dorm dorm = dormRepository.findByName(name);
        if (dorm == null) {
            log.error("Dorm with name {} not found", name);
            return null;
        }
        Room room = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .description(roomRequest.getDescription())
                .surface(roomRequest.getSurface())
                .price(roomRequest.getPrice())
                .build();
        dorm.getRooms().add(room);
        dormRepository.save(dorm);
        log.info("Room {} is saved in dorm {}", room.getRoomNumber(), dorm.getName());

        return room;
    }
    //@PreAuthorize("hasAuthority('USER')")
    public Room createRoom(RoomRequest roomRequest) {
        Room room = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .description(roomRequest.getDescription())
                .surface(roomRequest.getSurface())
                .price(roomRequest.getPrice())
                .build();
        roomRepository.save(room);

        log.info("Room {} is saved", room.getId());
        return room;
    }

    public List<RoomResponse> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    public RoomResponse getRoomByNumber(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null) {
            log.error("Room with number {} not found", roomNumber);
            return null;
        } else {
            return mapToRoomResponse(room);
        }
    }
    public RoomResponse getRoomById(String id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            return mapToRoomResponse(room);
        } else {
            log.error("Room with number {} not found", id);
            return null;
        }
    }
    public void updateRoom(String roomNumber, RoomRequest roomRequest) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null) {
            log.error("Room with number {} not found", roomNumber);
        } else {
            room.setRoomType(roomRequest.getRoomType());
            room.setDescription(roomRequest.getDescription());
            room.setSurface(roomRequest.getSurface());
            room.setPrice(roomRequest.getPrice());

            roomRepository.save(room);

            log.info("Room {} is updated", room.getId());
        }
    }

    public void deleteRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null) {
            log.error("Room with number {} not found", roomNumber);
        } else {
            roomRepository.delete(room);

            log.info("Room {} is deleted", room.getId());
        }
    }

    public RoomResponse mapToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .description(room.getDescription())
                .surface(room.getSurface())
                .price(room.getPrice())
                .build();
    }


    public Boolean checkAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        String availabilityUrl = "http://availability-service/api/availability/check?roomId="
                + roomId + "&startDate=" + startDate.toString() + "&endDate=" + endDate.toString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.getForEntity(availabilityUrl, Boolean.class);
        return response.getBody();
    }
}