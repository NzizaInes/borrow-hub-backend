package rw.auca.borrowhub.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.BorrowRequestStatus;
import rw.auca.borrowhub.repository.ApproverRepository;
import rw.auca.borrowhub.repository.BorrowRequestRepository;
import rw.auca.borrowhub.repository.DepartmentRepository;
import rw.auca.borrowhub.repository.ReturnInspectionRepository;
import rw.auca.borrowhub.repository.StaffMemberRepository;

@Service 
public class DashboardService {
    
    @Autowired 
    private StaffMemberRepository staffMemberRepo;

    @Autowired 
    private ApproverRepository approverRepo;

    @Autowired 
    private DepartmentRepository departmentRepo;

    @Autowired 
    private BorrowRequestRepository borrowRequestRepo;

    @Autowired
    private ReturnInspectionRepository returnInspectionRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("staffMembers", staffMemberRepo.count());
        summary.put("approvers", approverRepo.count());
        summary.put("freeDepartments", (long) departmentRepo.findFreeDepartments().size());
        summary.put("requestsToday", borrowRequestRepo.countByBorrowDate(LocalDate.now()));
        summary.put("pending", borrowRequestRepo.countByStatus(BorrowRequestStatus.PENDING));
        summary.put("returnInspection", returnInspectionRepo.count());

        return summary;
    }
}
