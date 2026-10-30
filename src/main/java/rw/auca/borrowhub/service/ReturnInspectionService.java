package rw.auca.borrowhub.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.BorrowRequestStatus;
import rw.auca.borrowhub.domain.ReturnInspection;
import rw.auca.borrowhub.repository.BorrowRequestRepository;
import rw.auca.borrowhub.repository.ReturnInspectionRepository;

@Service 
public class ReturnInspectionService {
    
    @Autowired 
    private ReturnInspectionRepository returnInspectionRepo;

    @Autowired 
    private BorrowRequestRepository borrowRequestRepo;

    public String saveReturnInspection(UUID borrowRequestId, ReturnInspection inspection) {
        Optional<BorrowRequest> requestOpt = borrowRequestRepo.findById(borrowRequestId);
        if (requestOpt.isEmpty()) {
            return "The borrow request with that id does not exist";
        }

        BorrowRequest request = requestOpt.get();

        if (request.getStatus() != BorrowRequestStatus.RETURNED){
            return "Return inspections are only for Returned borrow requests";
        }

        inspection.setBorrowRequest(request);
        inspection.setInspectedDate(LocalDate.now());
        returnInspectionRepo.save(inspection);
        return "Return inspection saved successfully";
    }

    public List<ReturnInspection> getByStaffNumber(String staffNumber) {
        return returnInspectionRepo.findByStaffMemberStaffNumber(staffNumber);
    }
    
    public String deleteReturnInspection(UUID id) {
        Optional<ReturnInspection> existing = returnInspectionRepo.findById(id);
        if (existing.isEmpty()) {
            return "The return inspection with that id does not exist";
        }
        returnInspectionRepo.deleteById(id);
        return "Return inspection deleted successfully";
    }
}
