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

import rw.auca.borrowhub.domain.Approver;
import rw.auca.borrowhub.service.ApproverService;

@RestController 
@RequestMapping(value = "/api/approvers")
public class ApproverController {
    
    @Autowired 
    private ApproverService approverService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveApprover(@RequestBody Approver approver) {
        String msg = approverService.saveApprover(approver);
        if (msg.equals("The department with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Approver>> getAllApprovers() {
        return new ResponseEntity<>(approverService.getAllApprovers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getApproverById(@PathVariable UUID id) {
        Optional<Approver> approver = approverService.getApproverById(id);
        if (approver.isPresent()) {
            return new ResponseEntity<>(approver.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The approver with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateApprover(@PathVariable UUID id, @RequestBody Approver approver) {
        String msg = approverService.updateApprover(id, approver);
        if (msg.equals("The approver with that id does not exist") || msg.equals("The department with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteApprover(@PathVariable UUID id) {
        String msg = approverService.deleteApprover(id);
        if (msg.equals("The approver with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/by-equipment-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Approver>> getApproversByEquipmentType(@RequestParam String name) {
        return new ResponseEntity<>(approverService.getApproversByEquipmentType(name), HttpStatus.OK);
    }
}
