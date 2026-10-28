package rw.auca.borrowhub.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.borrowhub.domain.EquipmentType;
import rw.auca.borrowhub.service.EquipmentTypeService;

@RestController 
@RequestMapping(value = "/api/equipment-types")
public class EquipmentTypeController {
    
    @Autowired 
    private EquipmentTypeService equipmentTypeService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveEquipmentType(@RequestBody EquipmentType equipmentType) {
        return new ResponseEntity<>(equipmentTypeService.saveEquipmentType(equipmentType), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentType>> getAllEquipmentTypes() {
        return new ResponseEntity<>(equipmentTypeService.getAllEquipmentTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEquipmentTypeById(@PathVariable UUID id) {
        Optional<EquipmentType> equipmentType = equipmentTypeService.getEquipmentTypeById(id);
        if (equipmentType.isPresent()) {
            return new ResponseEntity<>(equipmentType.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The equipment type with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEquipmentType(@PathVariable UUID id, @RequestBody EquipmentType equipmentType) {
        String msg = equipmentTypeService.updateEquipmentType(id, equipmentType);
        if (msg.equals("The equipment type with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEquipmentType(@PathVariable UUID id) {
        String msg = equipmentTypeService.deleteEquipmentType(id);
        if(msg.equals("The equipment type with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
