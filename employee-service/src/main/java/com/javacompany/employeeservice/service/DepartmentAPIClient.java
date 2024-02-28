package com.javacompany.employeeservice.service;

import com.javacompany.employeeservice.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:8081/api/department",value = "DEPARTMENT-SERVICE")
public interface DepartmentAPIClient {
    @GetMapping("/{deptcode}")
    DepartmentDTO getDepartment(@PathVariable("deptcode") String deptCode);

    }
