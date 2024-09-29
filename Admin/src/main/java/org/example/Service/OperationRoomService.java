package org.example.Service;

import org.example.Entity.OperationRoom;
import org.example.Repository.OperationRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationRoomService {

    @Autowired
    private OperationRoomRepository operationRoomRepository;

    public OperationRoom saveOperationRoom(OperationRoom room) {
        return operationRoomRepository.save(room);
    }

    public List<OperationRoom> getAllOperationRooms() {
        return operationRoomRepository.findAll();
    }

    public Optional<OperationRoom> getOperationRoomById(Long id) {
        return operationRoomRepository.findById(id);
    }

    public void deleteOperationRoom(Long id) {
        operationRoomRepository.deleteById(id);
    }
}
