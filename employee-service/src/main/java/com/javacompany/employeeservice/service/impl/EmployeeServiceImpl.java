package com.javacompany.employeeservice.service.impl;

import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.entity.Employee;
import com.javacompany.employeeservice.repository.EmployeeRepository;
import com.javacompany.employeeservice.service.DepartmentAPIClient;
import com.javacompany.employeeservice.service.EmployeeService;
import com.javacompany.employeeservice.utils.DefaultValues;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DepartmentAPIClient apiClient;
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        return modelMapper.map(employeeRepository.findById(id), EmployeeDTO.class);
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListBySorting(String field) {
        Pageable pageable = this.getPageableResponse(field,null,null);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPagination(Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(null,offset,pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field,offset,pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<ResponseDTO> getEmployeeListWithDepartmentsByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field,offset,pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(emp -> {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setEmployee(modelMapper.map(emp, EmployeeDTO.class));
            responseDTO.setDepartment(apiClient.getDepartment(emp.getDepartmentId()));
            return responseDTO;
        });
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByDepartmentIdWithPaginationAndSorting(String deptId, String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field,offset,pageSize);
        Page<Employee> employeePage = employeeRepository.findByDepartmentId(deptId, pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    private Pageable getPageableResponse ( String field, Integer offset, Integer pageSize){
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String validatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        return PageRequest.of(validatedOffset, validatedPageSize, Sort.by(Sort.Direction.ASC, validatedField));
    }
}


