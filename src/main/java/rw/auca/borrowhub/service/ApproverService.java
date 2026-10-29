package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.Department;
import rw.auca.borrowhub.domain.EquipmentType;
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

    public String addEquipmentTypeToApprover(UUID approverId, UUID typeId) {
        Optional<Approver> approverOpt = approverRepo.findById(approverId);
        if (approverOpt.isEmpty()) return "The approver with that id does not exist";

        Optional<EquipmentType> typeOpt = equipmentTypeRepo.findById(typeId);
        if (typeOpt.isEmpty()) return "That equipment type does not exist";

        Approver approver = approverOpt.get();
        EquipmentType type = typeOpt.get();

        boolean alreadyHas = approver.getEquipmentTypes().stream().anyMatch(t -> t.getId().equals(typeId));
        if (alreadyHas) return "The approver already has that equipment type";

        approver.getEquipmentTypes().add(type);
        approverRepo.save(approver);
        return "equipment type added to the approver";
    }

    public String changeDepartment(UUID approverId, int deptNumber) {
        Optional<Approver> approverOpt = approverRepo.findById(approverId);
        if (approverOpt.isEmpty()) return "The approver with that id does not exist";

        Optional<Department> deptOpt = departmentRepo.findByDeptNumber(deptNumber);
        if (deptOpt.isEmpty()) return "The department with that deptNumber does not exist";
        
        Department newDept = deptOpt.get();
        Approver approver = approverOpt.get();

        Optional<Approver> existingWithDept = approverRepo.findByDepartment(newDept);
        if (existingWithDept.isPresent() && !existingWithDept.get().getId().equals(approverId)) {
            return "That department is taken by another approver";
        }

        approver.setDepartment(newDept);
        approverRepo.save(approver);
        return "Department changed successfully";
    }
}
