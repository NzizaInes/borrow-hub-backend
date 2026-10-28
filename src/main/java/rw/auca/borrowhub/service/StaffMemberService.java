package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.StaffMember;
import rw.auca.borrowhub.repository.StaffMemberRepository;

@Service 
public class StaffMemberService {
    
    @Autowired 
    private StaffMemberRepository staffMemberRepo;

    public String saveStaffMember(StaffMember staffMember) {
        staffMemberRepo.save(staffMember);
        return "Staff member saved successfully";
    }

    public List<StaffMember> getAllStaffMembers() {
        return staffMemberRepo.findAll();
    }

    public Optional<StaffMember> getStaffMemberById(UUID id) {
        return staffMemberRepo.findById(id);
    }

    public String updateStaffMember(UUID id, StaffMember updated) {
        Optional<StaffMember> existing = staffMemberRepo.findById(id);
        if (existing.isEmpty()) {
            return "The staff member with that id does not exist";
        }
        StaffMember staffMember = existing.get();
        staffMember.setFirstName(updated.getFirstName());
        staffMember.setLastName(updated.getLastName());
        staffMember.setStaffNumber(updated.getStaffNumber());
        staffMember.setEmail(updated.getEmail());
        staffMemberRepo.save(staffMember);
        return "Staff member updated successfully";
    }

    public String deleteStaffMember(UUID id) {
        Optional<StaffMember> existing = staffMemberRepo.findById(id);
        if (existing.isEmpty()) {
            return "The staff member with that id does not exist";
        }
        staffMemberRepo.deleteById(id);
        return "Staff member deleted successfully";
    }
}
