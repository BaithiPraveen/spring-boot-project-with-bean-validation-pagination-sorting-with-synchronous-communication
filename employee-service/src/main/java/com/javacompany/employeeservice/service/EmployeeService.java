package com.javacompany.employeeservice.service;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployee(Long id);

    Page<EmployeeDTO> getEmployeeListBySorting(String field);

    Page<EmployeeDTO> getEmployeeListByPagination(Integer offset, Integer pageSize);

    Page<EmployeeDTO> getEmployeeListByPaginationAndSorting(String field, Integer offset, Integer pageSize);

    Page<ResponseDTO> getEmployeeListWithDepartmentsByPaginationAndSorting(String field, Integer offset, Integer pageSize);

    Page<EmployeeDTO> getEmployeeListByDepartmentIdWithPaginationAndSorting(String deptId, String field, Integer offset, Integer pageSize);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws IllegalAccessException, NoSuchFieldException;

    String deleteEmployee(Long id);

    ResponseDTO saveEmployeeWithDepartment(ResponseDTO responseDTO);

    EmployeeDTO searchByEmail(String email);

    Page<EmployeeDTO> searchByFirstNameOrLastName(String firstName, String lastName, String field, Integer offset, Integer pageSize);

    Page<EmployeeDTO> searchByFirstNameAndLastName(String firstName, String lastName, String field, Integer offset, Integer pageSize);
}
