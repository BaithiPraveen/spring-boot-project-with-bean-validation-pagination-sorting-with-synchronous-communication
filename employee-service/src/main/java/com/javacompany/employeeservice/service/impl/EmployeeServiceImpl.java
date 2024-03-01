package com.javacompany.employeeservice.service.impl;

import com.javacompany.employeeservice.ExceptionHandler.EmailValidationException;
import com.javacompany.employeeservice.ExceptionHandler.ResourceNotFoundException;
import com.javacompany.employeeservice.dto.DepartmentDTO;
import com.javacompany.employeeservice.dto.EmployeeDTO;
import com.javacompany.employeeservice.dto.ResponseDTO;
import com.javacompany.employeeservice.entity.Employee;
import com.javacompany.employeeservice.repository.EmployeeRepository;
import com.javacompany.employeeservice.service.DepartmentAPIClient;
import com.javacompany.employeeservice.service.EmployeeService;
import com.javacompany.employeeservice.utils.DefaultValues;
import com.javacompany.employeeservice.validation.EmailValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

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
    public ResponseDTO saveEmployeeWithDepartment(ResponseDTO responseDTO) {
        EmployeeDTO employeeDTO = responseDTO.getEmployee();
        DepartmentDTO departmentDTO = responseDTO.getDepartment();
        Boolean existDepartmentResult = apiClient.existsByDepartmentName(departmentDTO.getDepartmentName());
        DepartmentDTO resultdepartmentDTO = existDepartmentResult ?
                apiClient.searchByDepartmentName(departmentDTO.getDepartmentName()) :
                apiClient.saveDepartment(departmentDTO);
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setDepartmentId(resultdepartmentDTO.getDepartmentCode());
        EmployeeDTO resultEmployeeDTO = modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
        responseDTO.setEmployee(resultEmployeeDTO);
        responseDTO.setDepartment(resultdepartmentDTO);
        return responseDTO;
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EMPLOYEE", "EMPLOYEE-ID", id));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO searchByEmail(String email) {
        Employee employeeData = employeeRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("EMPLOYEE", "EMAIL", email));
        return modelMapper.map(employeeData, EmployeeDTO.class);
    }

    @Override
    public Page<EmployeeDTO> searchByFirstNameOrLastName(String firstName, String lastName, String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Employee> employeePage = employeeRepository.findByFirstNameOrLastName(firstName, lastName, pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> searchByFirstNameAndLastName(String firstName, String lastName, String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Employee> employeePage = employeeRepository.findByFirstNameAndLastName(firstName, lastName, pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListBySorting(String field) {
        Pageable pageable = this.getPageableResponse(field,null,null);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPagination(Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(null,offset,pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Page<ResponseDTO> getEmployeeListWithDepartmentsByPaginationAndSorting(String field, Integer offset, Integer pageSize) {
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
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
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Employee> employeePage = employeeRepository.findByDepartmentId(deptId, pageable);
        return employeePage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws IllegalAccessException, NoSuchFieldException {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("EMPLOYEE","EMPLOYEE-ID",id));
        Field[] fields = EmployeeDTO.class.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            Object value = field.get(employeeDTO);
            if (field.getName().equals("email")) {
                boolean isValidEmail = EmailValidation.isValidEmail(value.toString());
                if (!isValidEmail) throw new EmailValidationException(value.toString() + " is not a valid email");
            }
            if (value != null && !value.toString().isEmpty()) {
                Field employeeField = employee.getClass().getDeclaredField(field.getName());
                employeeField.setAccessible(true);
                employeeField.set(employee, value);
            }
        }
        return modelMapper.map(employeeRepository.save(employee),EmployeeDTO.class);
    }

    @Override
    public String deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("EMPLOYEE","EMPLOYEE-ID",id));
        employeeRepository.deleteById(id);
        return "Employee with Id  : " + id + " is Deleted Successfully...!";
    }

    private Pageable getPageableResponse ( String field, Integer offset, Integer pageSize){
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String validatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        return PageRequest.of(validatedOffset, validatedPageSize, Sort.by(Sort.Direction.ASC, validatedField));
    }
}


