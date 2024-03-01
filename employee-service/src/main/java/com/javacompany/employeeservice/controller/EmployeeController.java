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


@RestController
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employeeDTO));
    }

    @PostMapping("/createemployeewithdept")
    public ResponseEntity<ResponseDTO> saveEmployeeWithDepartment(@Valid @RequestBody ResponseDTO responseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployeeWithDepartment(responseDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) throws NoSuchFieldException, IllegalAccessException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/sort")
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListBySorting(@RequestParam(value = "field", required = false) String field) {
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
    public ResponseEntity<Page<EmployeeDTO>> getEmployeeListByDepartmentIdWithPaginationAndSorting(@PathVariable("deptId") String deptId,@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return ResponseEntity.ok(employeeService.getEmployeeListByDepartmentIdWithPaginationAndSorting(deptId,field, offset, pageSize));
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<EmployeeDTO> searchByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(employeeService.searchByEmail(email));
    }

    @GetMapping("/search/names/")
    public Page<EmployeeDTO> searchByFirstNameOrLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return employeeService.searchByFirstNameOrLastName(firstName, lastName, field, offset, pageSize);
    }

    @GetMapping("/search/name/")
    public Page<EmployeeDTO> searchByFirstNameAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field) {
        return employeeService.searchByFirstNameAndLastName(firstName, lastName, field, offset, pageSize);
    }

}
