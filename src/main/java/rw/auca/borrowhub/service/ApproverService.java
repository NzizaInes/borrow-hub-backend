package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.Department;
import rw.auca.borrowhub.repository.ApproverRepository;
import rw.auca.borrowhub.repository.DepartmentRepository;
import rw.auca.borrowhub.repository.EquipmentTypeRepository;

@Service 
public class ApproverService {
    @Autowired 
    private ApproverRepository approverRepo;

    @Autowired 
    private DepartmentRepository departmentRepo;

    @Autowired 
    private EquipmentTypeRepository equipmentTypeRepo;

    public String saveApprover(Approver approver) {
        if (approver.getDepartment() != null && approver.getDepartment().getId() != null) {
            Optional<Department> dept = departmentRepo.findById(approver.getDepartment().getId());
            if (dept.isEmpty()) {
                return "The department with that id does not exist";
            }
            approver.setDepartment(dept.get());
        } else {
            approver.setDepartment(null);
        }
        approverRepo.save(approver);
        return "Approver saved successfully";
    }
    public List<Approver> getAllApprovers() {
        return approverRepo.findAll();
    }

    public Optional<Approver> getApproverById(UUID id) {
        return approverRepo.findById(id);
    }

    public String updateApprover(UUID id, Approver updated) {
        Optional<Approver> existing = approverRepo.findById(id);
        if (existing.isEmpty()) {
            return "The approver with that id does not exist";
        }

        Approver approver = existing.get();
        approver.setFirstName(updated.getFirstName());
        approver.setLastName(updated.getLastName());
        approver.setEmail(updated.getEmail());

        if (updated.getDepartment() != null && updated.getDepartment().getId() != null) {
            Optional<Department> dept = departmentRepo.findById(updated.getDepartment().getId());
            if (dept.isEmpty()) {
                return "The department with that id does not exist";
            }
            approver.setDepartment(dept.get());
        } else {
            approver.setDepartment(null);
        }

        if (updated.getEquipmentTypes() != null) {
            approver.getEquipmentTypes().clear();
            for (var typeStub : updated.getEquipmentTypes()) {
                if (typeStub.getId() != null) {
                    var realType = equipmentTypeRepo.findById(typeStub.getId());
                    realType.ifPresent(approver.getEquipmentTypes()::add);
                }
            }
        }

        approverRepo.save(approver);
        return "Approver updated successfully";
    }

    public String deleteApprover(UUID id) {
        Optional<Approver> existing = approverRepo.findById(id);
        if(existing.isEmpty()){
            return "The approver with that id does not exist";
        }
        approverRepo.deleteById(id);
        return "Approver deleted successfully";
    }

    public List<Approver> getApproversByEquipmentType(String name) {
        return approverRepo.findByEquipmentTypeName(name);
    }
}
