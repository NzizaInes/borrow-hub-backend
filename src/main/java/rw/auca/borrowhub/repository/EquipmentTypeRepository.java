package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.EquipmentType;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, UUID> {
    List<EquipmentType> findByName(String name);
}
