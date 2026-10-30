package rw.auca.borrowhub.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.borrowhub.domain.ReturnInspection;
import rw.auca.borrowhub.service.ReturnInspectionService;

@RestController 
@RequestMapping(value = "/api/return-inspections")
public class ReturnInspectionController {
    
    @Autowired 
    private ReturnInspectionService returnInspectionService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveReturnInspection(
        @RequestParam UUID borrowRequestId,
        @RequestBody ReturnInspection inspection
    ) {
        String msg = returnInspectionService.saveReturnInspection(borrowRequestId, inspection);

        if (msg.equals("The borrow request with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        if (msg.equals("Return inspections are only for RETURNED borrow requests")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping(value = "/staff-member/{staffNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReturnInspection>> getByStaffNumber(@PathVariable String staffNumber) {
        return new ResponseEntity<>(returnInspectionService.getByStaffNumber(staffNumber), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteReturnInspection(@PathVariable UUID id) {
        String msg = returnInspectionService.deleteReturnInspection(id);
        if (msg.equals("The return inspection with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
