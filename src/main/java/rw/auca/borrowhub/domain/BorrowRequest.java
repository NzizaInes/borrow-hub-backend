package rw.auca.borrowhub.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrow_request")
public class BorrowRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BorrowRequestStatus status;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "days_requested")
    private int daysRequested;

    @ManyToOne
    @JoinColumn(name = "staff_member_id")
    private StaffMember staffMember;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Approver approver;

    public UUID getId() { 
        return id; 
    }
    public void setId(UUID id) { 
        this.id = id; 
    }
    public LocalDate getBorrowDate() { 
        return borrowDate; 
    }
    public void setBorrowDate(LocalDate borrowDate) { 
        this.borrowDate = borrowDate; 
    }
    public BorrowRequestStatus getStatus() { 
        return status; 
    }
    public void setStatus(BorrowRequestStatus status) { 
        this.status = status; 
    }
    public String getEquipmentName() { 
        return equipmentName; 
    }
    public void setEquipmentName(String equipmentName) { 
        this.equipmentName = equipmentName; 
    }
    public int getDaysRequested() { 
        return daysRequested; 
    }
    public void setDaysRequested(int daysRequested) { 
        this.daysRequested = daysRequested; 
    }
    public StaffMember getStaffMember() { 
        return staffMember; 
    }
    public void setStaffMember(StaffMember staffMember) { 
        this.staffMember = staffMember; 
    }
    public Approver getApprover() { 
        return approver; 
    }
    public void setApprover(Approver approver) { 
        this.approver = approver; 
    }
}
