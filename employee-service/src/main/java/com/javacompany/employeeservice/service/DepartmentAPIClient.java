package com.javacompany.employeeservice.service;

import com.javacompany.employeeservice.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8081/api/department",value = "DEPARTMENT-SERVICE")
public interface DepartmentAPIClient {

    @PostMapping
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);
    @GetMapping("/{deptcode}")
    DepartmentDTO getDepartment(@PathVariable("deptcode") String deptCode);

    @GetMapping("/exists/{deptName}")
    Boolean existsByDepartmentName(@PathVariable("deptName") String departmentName);

    @GetMapping("/search/{deptName}")
    DepartmentDTO searchByDepartmentName(@PathVariable("deptName") String departmentName);

}
