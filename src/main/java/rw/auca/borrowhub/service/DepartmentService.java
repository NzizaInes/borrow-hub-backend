package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.Department;
import rw.auca.borrowhub.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;

    public String saveDepartment(Department department) {
        departmentRepo.save(department);
        return "Department saved successfully";
    }

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public Optional<Department> getDepartmentById(UUID id) {
        return departmentRepo.findById(id);
    }

    public String updateDepartment(UUID id, Department updated) {
        Optional<Department> existing = departmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The department with that id does not exist";
        }
        Department department = existing.get();
        department.setDeptNumber(updated.getDeptNumber());
        department.setName(updated.getName());
        departmentRepo.save(department);
        return "Department updated successfully";
    }

    public String deleteDepartment(UUID id) {
        Optional<Department> existing = departmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The department with that id does not exist";
        }
        departmentRepo.deleteById(id);
        return "Department deleted successfully";
    }
}