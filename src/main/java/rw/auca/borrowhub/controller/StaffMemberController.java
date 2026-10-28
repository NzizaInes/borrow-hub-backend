package rw.auca.borrowhub.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.borrowhub.domain.StaffMember;
import rw.auca.borrowhub.service.StaffMemberService;

@RestController 
@RequestMapping(value = "/api/staff-members")
public class StaffMemberController {
    
    @Autowired 
    private StaffMemberService staffMemberService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStaffMember(@RequestBody StaffMember staffMember) {
        return new ResponseEntity<>(staffMemberService.saveStaffMember(staffMember), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffMember>> getAllStaffMembers() {
        return new ResponseEntity<>(staffMemberService.getAllStaffMembers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStaffMemberById(@PathVariable UUID id) {
        Optional<StaffMember> staffMember = staffMemberService.getStaffMemberById(id);
        if (staffMember.isPresent()) {
            return new ResponseEntity<>(staffMember.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The staff member with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStaffMember(@PathVariable UUID id, @RequestBody StaffMember staffMember) {
        String msg = staffMemberService.updateStaffMember(id, staffMember);
        if (msg.equals("The staff member with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStaffMember(@PathVariable  UUID id) {
        String msg = staffMemberService.deleteStaffMember(id);
        if(msg.equals("The staff member with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/by-last-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffMember>> getStaffMembersByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(staffMemberService.getStaffMembersByLastName(lastName), HttpStatus.OK);
    }
}
