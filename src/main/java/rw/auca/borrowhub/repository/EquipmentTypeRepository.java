package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.borrowhub.domain.EquipmentType;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, UUID> {
    List<EquipmentType> findByName(String name);

    @Query("SELECT e FROM EquipmentType e WHERE e.approvers IS EMPTY")
    List<EquipmentType> findUnusedEquipmentTypes();
}
