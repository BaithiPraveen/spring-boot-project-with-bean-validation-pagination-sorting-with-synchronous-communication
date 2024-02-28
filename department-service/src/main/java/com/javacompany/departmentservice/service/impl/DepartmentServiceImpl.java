package com.javacompany.departmentservice.service.impl;

import com.javacompany.departmentservice.dto.DepartmentDTO;
import com.javacompany.departmentservice.dto.EmployeeDTO;
import com.javacompany.departmentservice.entity.Department;
import com.javacompany.departmentservice.repository.DepartmentRepository;
import com.javacompany.departmentservice.service.DepartmentService;
import com.javacompany.departmentservice.utils.DefaultValues;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        return modelMapper.map(departmentRepository.save(department), DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO getDepartment(String deptCode) {
        return modelMapper.map(departmentRepository.findByDepartmentCode(deptCode), DepartmentDTO.class);
    }

    @Override
    public Page<DepartmentDTO> getDepartmentListWithPaginationAndSorting(Integer offset, Integer pageSize, String field) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String ValidatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        Page<Department> departmentPage = departmentRepository.findAll(PageRequest.of(validatedOffset, validatedPageSize).withSort(Sort.Direction.ASC, ValidatedField));
        return departmentPage.map(department -> modelMapper.map(department, DepartmentDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListWithDepartmentName(String departmentName, Integer offset, Integer pageSize, String field) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String ValidatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        Department departmentObject = departmentRepository.findByDepartmentName(departmentName);
        List<EmployeeDTO> employeesDTO = webClient
                .get()
                .uri("http://localhost:8080/api/employee/dept/" + departmentObject.getDepartmentCode())
                .retrieve()
                .bodyToFlux(EmployeeDTO.class)
                .collectList()
                .block();
        return new PageImpl<>(employeesDTO, PageRequest.of(validatedOffset, validatedPageSize).withSort(Sort.Direction.ASC,ValidatedField), employeesDTO.size());
    }
}
