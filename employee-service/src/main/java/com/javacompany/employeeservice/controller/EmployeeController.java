package com.javacompany.employeeservice.controller;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employeeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeListBySorting(@RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(employeeService.getEmployeeListBySorting(field));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListByPagination(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(employeeService.getEmployeeListByPagination(offset, pageSize));
    }

    @GetMapping("/paginationandsort/")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListByPaginationAndSorting(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(employeeService.getEmployeeListByPaginationAndSorting(field, offset, pageSize));
    }

    @GetMapping("/dept/paginationandsort/")
    public ResponseEntity<Page<ResponseDTO>> getEmployeeListWithDepartmentsByPaginationAndSorting(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        Page<ResponseDTO> dtoPage = employeeService.getEmployeeListWithDepartmentsByPaginationAndSorting(field, offset, pageSize);
        return ResponseEntity.ok(dtoPage);
    }
    @GetMapping("/dept/{deptId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeListByDepartmentIdWithPaginationAndSorting(@PathVariable("deptId") String deptId,@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(employeeService.getEmployeeListByDepartmentIdWithPaginationAndSorting(deptId,field, offset, pageSize));
    }
}
