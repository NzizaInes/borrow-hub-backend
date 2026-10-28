package rw.auca.borrowhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.BorrowRequest;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, UUID>{
    boolean existsByStaffMemberIdAndApproverId(UUID staffMemberId, UUID approverId);
}
