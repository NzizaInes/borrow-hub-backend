package rw.auca.borrowhub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByDeptNumber(int deptNumber);
}
