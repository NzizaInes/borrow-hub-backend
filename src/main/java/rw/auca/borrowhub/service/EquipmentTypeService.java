package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.EquipmentType;
import rw.auca.borrowhub.repository.EquipmentTypeRepository;

@Service 
public class EquipmentTypeService {
    
    @Autowired 
    private EquipmentTypeRepository equipmentTypeRepo;

    public String saveEquipmentType(EquipmentType equipmentType) {
        equipmentTypeRepo.save(equipmentType);
        return "Equipment type saved successfully";
    }

    public List<EquipmentType> getAllEquipmentTypes() {
        return equipmentTypeRepo.findAll();
    }

    public Optional<EquipmentType> getEquipmentTypeById(UUID id) {
        return equipmentTypeRepo.findById(id);
    }

    public String updateEquipmentType(UUID id, EquipmentType updated) {
        Optional<EquipmentType> existing = equipmentTypeRepo.findById(id);
        if (existing.isEmpty()) {
            return "The equipment type with that id does not exist";
        }
        EquipmentType equipmentType = existing.get();
        equipmentType.setName(updated.getName());
        equipmentTypeRepo.save(equipmentType);
        return "Equipment type updated successfully";
    }

    public String deleteEquipmentType(UUID id) {
        Optional<EquipmentType> existing = equipmentTypeRepo.findById(id);
        if (existing.isEmpty()) {
            return "The equipment type with that id does not exist";
        }
        equipmentTypeRepo.deleteById(id);
        return "Equipment type deleted successfully";
    }

    public List<EquipmentType> getUnusedEquipmentTypes() {
        return equipmentTypeRepo.findUnusedEquipmentTypes();
    }
}
