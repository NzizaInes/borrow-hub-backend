package rw.auca.borrowhub.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.BorrowRequestStatus;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, UUID>{
    boolean existsByStaffMemberIdAndApproverId(UUID staffMemberId, UUID approverId);
    List<BorrowRequest> findByStatusOrderByBorrowDateAsc(BorrowRequestStatus status);
    List<BorrowRequest> findByBorrowDateBetweenOrderByBorrowDateAsc(LocalDate start, LocalDate end);
    boolean existsByApproverIdAndBorrowDateAndStatusNot(UUID approverId, LocalDate borrowDate, BorrowRequestStatus status);
    @Query("SELECT b.status, COUNT(b) FROM BorrowRequest b GROUP BY b.status")
    List<Object[]> countBorrowRequestByStatus();

    @Modifying 
    @Transactional 
    @Query("UPDATE BorrowRequest b SET b.status = :newStatus " +
    "WHERE b.approver.id = :approverId " +
    "And b.borrowDate = :date " +
    "AND b.status <> :excludeStatus")
    int cancelBorrowRequestsForApproverOnDate(
        @Param("approverId") UUID approverId,
        @Param("date") LocalDate date,
        @Param("newStatus") BorrowRequestStatus newStatus,
        @Param("excludeStatus") BorrowRequestStatus excludeStatus);

    long countByBorrowDate(LocalDate borrowDate);
    long countByStatus(BorrowRequestStatus status);
}
