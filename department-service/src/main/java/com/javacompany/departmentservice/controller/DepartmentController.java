package com.javacompany.departmentservice.controller;

import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
import com.javacompany.departmentservice.entity.Department;
import com.javacompany.departmentservice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.saveDepartment(departmentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentDTO departmentDTO) throws NoSuchFieldException, IllegalAccessException {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }

    @GetMapping("/{deptcode}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("deptcode") String deptCode) {
        return ResponseEntity.ok(departmentService.getDepartment(deptCode));
    }

    @GetMapping("/category/{deptName}")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListWithDepartmentName(@PathVariable("deptName") String deptName, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(departmentService.getEmployeeListWithDepartmentName(deptName, offset, pageSize, field));
    }

    @GetMapping("/exists/{deptName}")
    public ResponseEntity<Boolean> existsByDepartmentName(@PathVariable("deptName") String departmentName) {
        return ResponseEntity.ok(departmentService.existsByDepartmentName(departmentName));
    }

    @GetMapping("/search/{deptName}")
    public ResponseEntity<Department> searchByDepartmentName(@PathVariable("deptName") String departmentName) {
        return ResponseEntity.ok(departmentService.searchByDepartmentName(departmentName));
    }

    @GetMapping("/paginationandsort/")
    public ResponseEntity<Page<DepartmentDTO>> getDepartmentListWithPaginationAndSorting(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(departmentService.getDepartmentListWithPaginationAndSorting(offset, pageSize, field));
    }

}
