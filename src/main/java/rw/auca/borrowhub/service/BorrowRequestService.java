package rw.auca.borrowhub.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.BorrowRequestStatus;
import rw.auca.borrowhub.domain.StaffMember;
import rw.auca.borrowhub.repository.ApproverRepository;
import rw.auca.borrowhub.repository.BorrowRequestRepository;
import rw.auca.borrowhub.repository.ReturnInspectionRepository;
import rw.auca.borrowhub.repository.StaffMemberRepository;

@Service 
public class BorrowRequestService {
    @Autowired 
    private BorrowRequestRepository borrowRequestRepo;

    @Autowired 
    private StaffMemberRepository staffMemberRepo;

    @Autowired 
    private ApproverRepository approverRepo;

    @Autowired 
    private ReturnInspectionRepository returnInspectionRepo;

    public String saveBorrowRequest(BorrowRequest request) {
        if (request.getStaffMember() == null || request.getStaffMember().getId() == null) {
            return "Staff member id is required";
        }
        if (request.getApprover() == null || request.getApprover().getId() == null) {
            return "Approver id is required";
        }
        if (request.getBorrowDate().isBefore(java.time.LocalDate.now())) {
            return "Borrow request date cannot be in the past";
        }

        Optional<StaffMember> staff = staffMemberRepo.findById(request.getStaffMember().getId());
        if (staff.isEmpty()) return "The staff member with that id does not exist";

        Optional<Approver> approver = approverRepo.findById(request.getApprover().getId());
        if (approver.isEmpty()) return "The approver with that id does not exist";

        boolean alreadyBooked = borrowRequestRepo.existsByApproverIdAndBorrowDateAndStatusNot(
            request.getApprover().getId(),
            request.getBorrowDate(),
            BorrowRequestStatus.REJECTED
        );

        if (alreadyBooked) {
            return "Approver already has a borrow request on that date";
        }

        request.setStaffMember(staff.get());
        request.setApprover(approver.get());

        request.setStatus(BorrowRequestStatus.PENDING);

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
            return "That borrow request with that id does not exist";
        }

        if (returnInspectionRepo.existsByBorrowRequest(existing.get())) {
            return "That borrow request has return inspections, delete them first";
        }

        borrowRequestRepo.deleteById(id);
        return "Borrow request deleted successfully";
    }

    public List<BorrowRequest> getBorrowRequestsByStatus(BorrowRequestStatus status) {
        return borrowRequestRepo.findByStatusOrderByBorrowDateAsc(status);
    }

    public List<BorrowRequest> getBorrowRequestsBetween(LocalDate start, LocalDate end) {
        return borrowRequestRepo.findByBorrowDateBetweenOrderByBorrowDateAsc(start, end);
    }

    public String changeStatus(UUID id, BorrowRequestStatus newStatus) {
        Optional<BorrowRequest> existing = borrowRequestRepo.findById(id);
        if (existing.isEmpty()) {
            return "The borrow request with that id does not exist";
        }
        
        BorrowRequest request = existing.get();
        BorrowRequestStatus current = request.getStatus();

        boolean allowed = false;
        if (current == BorrowRequestStatus.PENDING && (newStatus == BorrowRequestStatus.APPROVED || newStatus == BorrowRequestStatus.REJECTED)) allowed = true;

        else if (current == BorrowRequestStatus.APPROVED && (newStatus == BorrowRequestStatus.RETURNED || newStatus == BorrowRequestStatus.REJECTED)) allowed = true;

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        request.setStatus(newStatus);
        borrowRequestRepo.save(request);
        return "Borrow request status updated successfully";
    }

    public List<Object[]> getBorrowRequestStatsByStatus() {
        return borrowRequestRepo.countBorrowRequestByStatus();
    }

    public String cancelApproverDay(UUID approverId, LocalDate date) {
        int updated = borrowRequestRepo.cancelBorrowRequestsForApproverOnDate(
            approverId, 
            date,
            BorrowRequestStatus.REJECTED,
            BorrowRequestStatus.RETURNED
        );
        return updated + "borrow requests cancelled";
    }

    public Page<BorrowRequest> getBorrowRequestsPage(Pageable pageable) {
        return borrowRequestRepo.findAll(pageable);
    }
}
