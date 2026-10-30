package rw.auca.borrowhub.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "return_inspection")
public class ReturnInspection {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "condition")
    private String condition;

    @Column(name = "notes")
    private String notes;

    @Column(name = "days_overdue")
    private int daysOverdue;

    @Column(name = "inspected_date")
    private LocalDate inspectedDate;

    @ManyToOne 
    @JoinColumn(name = "borrow_request_id")
    private BorrowRequest borrowRequest;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }
    public void setDaysOverdue(int daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public LocalDate getInspectedDate() {
        return inspectedDate;
    }
    public void setInspectedDate(LocalDate inspectedDate) {
        this.inspectedDate = inspectedDate;
    }

    public BorrowRequest getBorrowRequest() {
        return borrowRequest;
    }
    public void setBorrowRequest(BorrowRequest borrowRequest) {
        this.borrowRequest = borrowRequest;
    }

}
