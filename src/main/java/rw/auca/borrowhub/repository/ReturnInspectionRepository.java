package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.domain.ReturnInspection;

public interface ReturnInspectionRepository extends JpaRepository<ReturnInspection, UUID> {
    boolean existsByBorrowRequest(BorrowRequest borrowRequest);

    @Query("SELECT r FROM ReturnInspection r WHERE r.borrowRequest.staffMember.staffNumber = :staffNumber ORDER BY r.inspectedDate DESC")
    List<ReturnInspection> findByStaffMemberStaffNumber(@Param("staffNumber") String staffNumber);
}
