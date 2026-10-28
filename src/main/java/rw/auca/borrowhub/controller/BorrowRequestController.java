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
import org.springframework.web.bind.annotation.RestController;

import rw.auca.borrowhub.domain.BorrowRequest;
import rw.auca.borrowhub.service.BorrowRequestService;

@RestController 
@RequestMapping(value = "/api/borrow-requests")
public class BorrowRequestController {
    
    @Autowired 
    private BorrowRequestService borrowRequestService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveBorrowRequest(@RequestBody BorrowRequest borrowRequest) {
        String msg = borrowRequestService.saveBorrowRequest(borrowRequest);
        if (msg.contains("does not exist") || msg.contains("is required")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BorrowRequest>> getAllBorrowRequests() {
        return new ResponseEntity<>(borrowRequestService.getAllBorrowRequests(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBorrowRequestById(@PathVariable UUID id) {
        Optional<BorrowRequest> request = borrowRequestService.getBorrowRequestById(id);
        if (request.isPresent()) {
            return new ResponseEntity<>(request.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The borrow request with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uodateBorrowRequest(@PathVariable UUID id, @RequestBody BorrowRequest borrowRequest) {
        String msg = borrowRequestService.updateBorrowRequest(id, borrowRequest);
        if(msg.equals("The borrow request with that id does not exist") || msg.contains("does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBorrowRequest(@PathVariable UUID id) {
        String msg = borrowRequestService.deleteBorrowRequest(id);
        if (msg.equals("The borrow request with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
