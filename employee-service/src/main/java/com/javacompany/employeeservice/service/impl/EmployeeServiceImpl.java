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
    public List<EmployeeDTO> getEmployeeListBySorting(String field) {
        List<Employee> employeeList;
        if (field == null || field.isEmpty() || field.isBlank())
            employeeList = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, DefaultValues.DEFAULT_FIELD));
        else employeeList = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPagination(Integer offset, Integer pageSize) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(validatedOffset, validatedPageSize));
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String ValidatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(validatedOffset, validatedPageSize).withSort(Sort.Direction.ASC, ValidatedField));
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<ResponseDTO> getEmployeeListWithDepartmentsByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String validatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(validatedOffset, validatedPageSize).withSort(Sort.Direction.ASC, validatedField));
        return employeePage.map(emp -> {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setEmployee(modelMapper.map(emp, EmployeeDTO.class));
            responseDTO.setDepartment(apiClient.getDepartment(emp.getDepartmentId()));
            return responseDTO;
        });
    }

    @Override
    public List<EmployeeDTO> getEmployeeListByDepartmentIdWithPaginationAndSorting( String deptId,String field, Integer offset, Integer pageSize) {
        List<Employee> byDepartmentId = employeeRepository.findByDepartmentId(deptId);
        return byDepartmentId.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }
}


