package com.javacompany.departmentservice.service.impl;

import com.javacompany.departmentservice.ExceptionHandler.ResourceNotFoundException;
import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
import com.javacompany.departmentservice.entity.Department;
import com.javacompany.departmentservice.repository.DepartmentRepository;
import com.javacompany.departmentservice.service.DepartmentService;
import com.javacompany.departmentservice.service.EmployeeAPIClient;
import com.javacompany.departmentservice.utils.DefaultValues;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeAPIClient apiClient;

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        return modelMapper.map(departmentRepository.save(department), DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO getDepartment(String deptCode) {
        Department department = departmentRepository.findByDepartmentCode(deptCode).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT", "DEPARTMENT_CODE", deptCode));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public Page<DepartmentDTO> getDepartmentListWithPaginationAndSorting(Integer offset, Integer pageSize, String field) {
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);
        return departmentPage.map(department -> modelMapper.map(department, DepartmentDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListWithDepartmentName(String departmentName, Integer offset, Integer pageSize, String field) {
        Department departmentObject = departmentRepository.findByDepartmentName(departmentName).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT", "DEPARTMENT_NAME", departmentName));
        String departmentCode = departmentObject.getDepartmentCode();
        return apiClient.getEmployeeListByDepartmentIdWithPaginationAndSorting(departmentCode, offset, pageSize, field);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) throws IllegalAccessException, NoSuchFieldException {
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("DEPARTMENT","DEPARTMENT_ID",id));
        Field[] fields = DepartmentDTO.class.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            Object value = field.get(departmentDTO);
            if(value !=null){
                Field declaredField = department.getClass().getDeclaredField(field.getName());
                declaredField.setAccessible(true);
                declaredField.set(department,value);
            }
        }
        return modelMapper.map(departmentRepository.save(department),DepartmentDTO.class);
    }

    @Override
    public Boolean existsByDepartmentName(String departmentName) {
        return departmentRepository.existsByDepartmentName(departmentName);
    }

    @Override
    public Department searchByDepartmentName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT", "DEPARTMENT_NAME", departmentName));
    }

    @Override
    public String deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("DEPARTMENT","DEPARTMENT_ID",id));
        departmentRepository.deleteById(id);
        return id + " THIS DEPARTMENT SUCCESSFULLY DELETED...!";
    }

    private Pageable getPageableResponse(String field, Integer offset, Integer pageSize) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String validatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        return PageRequest.of(validatedOffset, validatedPageSize, Sort.by(Sort.Direction.ASC, validatedField));
    }
}
