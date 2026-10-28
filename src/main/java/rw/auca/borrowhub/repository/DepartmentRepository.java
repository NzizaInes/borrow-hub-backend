package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.borrowhub.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByDeptNumber(int deptNumber);

    @Query("SELECT d FROM Department d WHERE NOT EXISTS (SELECT a FROM Approver a WHERE a.department = d) ORDER BY d.deptNumber ASC")
    List<Department> findFreeDepartments();
}
