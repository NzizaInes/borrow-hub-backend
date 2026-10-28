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

import rw.auca.borrowhub.domain.Department;
import rw.auca.borrowhub.service.DepartmentService;

@RestController 
@RequestMapping(value = "/api/departments")
public class DepartmentController {
    
    @Autowired 
    private DepartmentService departmentService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.saveDepartment(department), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDepartmentById(@PathVariable UUID id) {
        Optional<Department> dept = departmentService.getDepartmentById(id);
        if (dept.isPresent()) {
            return new ResponseEntity<>(dept.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The department with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDepartment(@PathVariable UUID id, @RequestBody Department department) {
        String msg = departmentService.updateDepartment(id, department);
        if (msg.equals("The department with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteDepartment(@PathVariable UUID id) {
        String msg = departmentService.deleteDepartment(id);
        if (msg.equals("The department with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/free", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getFreeDepartments() {
        return new ResponseEntity<>(departmentService.getFreeDepartments(), HttpStatus.OK);
    }
}
