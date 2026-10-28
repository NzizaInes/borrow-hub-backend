package rw.auca.borrowhub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.Department;

public interface ApproverRepository extends JpaRepository<Approver, UUID> {
    Optional<Approver> findByDepartment(Department department);
    
}
