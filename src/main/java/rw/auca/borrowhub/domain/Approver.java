package rw.auca.borrowhub.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "approver")
public class Approver {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany 
    @JoinTable(
        name = "approver_equipment_type",
        joinColumns = @JoinColumn(name = "approver_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_type_id")
    )

    private List<EquipmentType> equipmentTypes = new ArrayList<>();
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public Department getDepartment() { 
        return department; 
    }
    public void setDepartment(Department department) { 
        this.department = department; 
    }
    public List<EquipmentType> getEquipmentTypes() { 
        return equipmentTypes; 
    }
    public void setEquipmentTypes(List<EquipmentType> equipmentTypes) { 
        this.equipmentTypes = equipmentTypes; 
    }
}
