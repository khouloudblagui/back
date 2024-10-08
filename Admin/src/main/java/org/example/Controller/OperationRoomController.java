package org.example.Controller;

import org.example.Entity.OperationRoom;
import org.example.Service.OperationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/operation-rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class OperationRoomController {

    @Autowired
    private OperationRoomService operationRoomService;

    @PostMapping
    public ResponseEntity<OperationRoom> createOperationRoom(@RequestBody OperationRoom room) {
        return ResponseEntity.ok(operationRoomService.saveOperationRoom(room));
    }

    @GetMapping
    public ResponseEntity<List<OperationRoom>> getAllOperationRooms() {
        return ResponseEntity.ok(operationRoomService.getAllOperationRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationRoom> getOperationRoomById(@PathVariable Long id) {
        return operationRoomService.getOperationRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationRoom(@PathVariable Long id) {
        operationRoomService.deleteOperationRoom(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<OperationRoom> updateOperationRoom(@PathVariable Long id, @RequestBody OperationRoom roomDetails) {
        return operationRoomService.getOperationRoomById(id)
                .map(existingRoom -> {
                    existingRoom.setPatientName(roomDetails.getPatientName());
                    existingRoom.setRoomNumber(roomDetails.getRoomNumber());
                    existingRoom.setRoomType(roomDetails.getRoomType());
                    existingRoom.setGender(roomDetails.getGender());
                    existingRoom.setAdmitDate(roomDetails.getAdmitDate());
                    existingRoom.setDischargeDate(roomDetails.getDischargeDate());
                    existingRoom.setStatus(roomDetails.getStatus());
                    return ResponseEntity.ok(operationRoomService.saveOperationRoom(existingRoom));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
