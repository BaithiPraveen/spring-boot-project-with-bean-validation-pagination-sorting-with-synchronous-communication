package com.javacompany.departmentservice.controller;

import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
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

    @GetMapping("/{deptcode}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("deptcode") String deptCode) {
        return ResponseEntity.ok(departmentService.getDepartment(deptCode));
    }

    @GetMapping("/paginationandsort/")
    public ResponseEntity<Page<DepartmentDTO>> getDepartmentListWithPaginationAndSorting(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(departmentService.getDepartmentListWithPaginationAndSorting(offset, pageSize, field));
    }

    @GetMapping("/category/{deptName}")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListWithDepartmentName(@PathVariable("deptName") String deptName, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(departmentService.getEmployeeListWithDepartmentName(deptName, offset, pageSize, field));
    }

}
