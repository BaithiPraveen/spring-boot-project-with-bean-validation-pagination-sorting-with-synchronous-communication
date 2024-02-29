package com.javacompany.departmentservice.service;

import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartment(String deptCode);

    Page<DepartmentDTO> getDepartmentListWithPaginationAndSorting(Integer offset, Integer pageSize, String field);

    List<EmployeeDTO> getEmployeeListWithDepartmentNameExample1(String departmentName, Integer offset, Integer pageSize, String field);

    Page<EmployeeDTO> getEmployeeListWithDepartmentName(String departmentName, Integer offset, Integer pageSize, String field);
}
