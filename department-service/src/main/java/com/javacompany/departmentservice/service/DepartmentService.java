package com.javacompany.departmentservice.service;

import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
import com.javacompany.departmentservice.entity.Department;
import org.springframework.data.domain.Page;


public interface DepartmentService {
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartment(String deptCode);
    Page<DepartmentDTO> getDepartmentListWithPaginationAndSorting(Integer offset, Integer pageSize, String field);
    Page<EmployeeDTO> getEmployeeListWithDepartmentName(String departmentName, Integer offset, Integer pageSize, String field);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) throws IllegalAccessException, NoSuchFieldException;
    String deleteDepartment(Long id);

    Boolean existsByDepartmentName(String departmentName);

    Department searchByDepartmentName(String departmentName);
}
