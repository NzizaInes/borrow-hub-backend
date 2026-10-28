package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.Department;

public interface ApproverRepository extends JpaRepository<Approver, UUID> {
    Optional<Approver> findByDepartment(Department department);
    
    @Query("SELECT DISTINCT a FROM Approver a JOIN a.equipmentTypes e WHERE LOWER(e.name) = LOWER(:name)")
    List<Approver> findByEquipmentTypeName(@Param("name") String name);
}
