package com.javacompany.departmentservice.service;

import com.javacompany.departmentservice.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EMPLOYEE-SERVICE",url = "http://localhost:8080/api/employee/")
public interface EmployeeAPIClient {
    @GetMapping("/dept/{deptId}")
    Page<EmployeeDTO> getEmployeeListByDepartmentIdWithPaginationAndSorting(@PathVariable("deptId") String deptId, @RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "field", required = false) String field);
}
