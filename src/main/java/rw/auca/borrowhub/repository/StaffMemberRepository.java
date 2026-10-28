package rw.auca.borrowhub.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.borrowhub.domain.StaffMember;

public interface StaffMemberRepository extends JpaRepository<StaffMember, UUID> {
    Optional<StaffMember> findByStaffNumber(String staffNumber);
    List<StaffMember> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
