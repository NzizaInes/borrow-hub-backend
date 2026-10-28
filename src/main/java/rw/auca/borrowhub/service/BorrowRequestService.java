package rw.auca.borrowhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.BorrowRequestStatus;
import rw.auca.borrowhub.domain.StaffMember;
import rw.auca.borrowhub.repository.ApproverRepository;
import rw.auca.borrowhub.repository.BorrowRequestRepository;
import rw.auca.borrowhub.repository.StaffMemberRepository;

@Service 
public class BorrowRequestService {
    @Autowired 
    private BorrowRequestRepository borrowRequestRepo;

    @Autowired 
    private StaffMemberRepository staffMemberRepo;

    @Autowired 
    private ApproverRepository approverRepo;

    public String saveBorrowRequest(BorrowRequest request) {
        if (request.getStaffMember() == null || request.getStaffMember().getId() == null) {
            return "staff member id is required";
        }
        if (request.getApprover() == null || request.getApprover().getId() == null) {
            return "Approver id is required";
        }

        Optional<StaffMember> staff = staffMemberRepo.findById(request.getStaffMember().getId());
        if (staff.isEmpty()) {
            return "The staff member with that id does not exist";
        }

        Optional<Approver> approver = approverRepo.findById(request.getApprover().getId());
        if (approver.isEmpty()) {
            return "The approver with that id does not exist";
        }

        request.setStaffMember(staff.get());
        request.setApprover(approver.get());

        if (request.getStatus() == null) {
            request.setStatus(BorrowRequestStatus.PENDING);
        }

        borrowRequestRepo.save(request);
        return "Borrow request saved successfully";
    }

    public List<BorrowRequest> getAllBorrowRequests() {
        return borrowRequestRepo.findAll();
    }

    public Optional<BorrowRequest> getBorrowRequestById(UUID id) {
        return borrowRequestRepo.findById(id);
    }

    public String updateBorrowRequest(UUID id, BorrowRequest updated) {
        Optional<BorrowRequest> existing = borrowRequestRepo.findById(id);
        if (existing.isEmpty()) {
            return "The borrow request with that id does not exist";
        }

        BorrowRequest request = existing.get();
        request.setBorrowDate(updated.getBorrowDate());
        request.setEquipmentName(updated.getEquipmentName());
        request.setDaysRequested(updated.getDaysRequested());

        request.setStatus(updated.getStatus());

        if (updated.getStaffMember() != null && updated.getStaffMember().getId() != null) {
            Optional<StaffMember> staff = staffMemberRepo.findById(updated.getStaffMember().getId());
            if (staff.isEmpty()) return "The staff member with that id does not exist";
            request.setStaffMember(staff.get());
        }

        if (updated.getApprover() != null && updated.getApprover().getId() != null) {
            Optional<Approver> approver = approverRepo.findById(updated.getApprover().getId());
            if (approver.isEmpty()) return "The approver with that id does not exist";
            request.setApprover(approver.get());
        }

        borrowRequestRepo.save(request);
        return "Borrow request updated successfully";
    }

    public String deleteBorrowRequest(UUID id) {
        Optional<BorrowRequest> existing = borrowRequestRepo.findById(id);
        if (existing.isEmpty()) {
            return "The borrow request with that id does not exist";
        }
        borrowRequestRepo.deleteById(id);
        return "Borrow request deleted successfully";
    }
}
