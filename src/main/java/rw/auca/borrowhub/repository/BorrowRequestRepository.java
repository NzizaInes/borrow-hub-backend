package rw.auca.borrowhub.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.BorrowRequestStatus;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, UUID>{
    boolean existsByStaffMemberIdAndApproverId(UUID staffMemberId, UUID approverId);
    List<BorrowRequest> findByStatusOrderByBorrowDateAsc(BorrowRequestStatus status);
    List<BorrowRequest> findByBorrowDateBetweenOrderByBorrowDateAsc(LocalDate start, LocalDate end);
}
