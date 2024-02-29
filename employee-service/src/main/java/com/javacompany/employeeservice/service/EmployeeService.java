package com.javacompany.employeeservice.service;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployee(Long id);

    Page<EmployeeDTO> getEmployeeListBySorting(String field);

    Page<EmployeeDTO> getEmployeeListByPagination(Integer offset, Integer pageSize);

    Page<EmployeeDTO> getEmployeeListByPaginationAndSorting(String field, Integer offset, Integer pageSize);

    Page<ResponseDTO> getEmployeeListWithDepartmentsByPaginationAndSorting(String field, Integer offset, Integer pageSize);

    Page<EmployeeDTO> getEmployeeListByDepartmentIdWithPaginationAndSorting(String deptId, String field, Integer offset, Integer pageSize);
}
