package com.javacompany.departmentservice.service.impl;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final EmployeeAPIClient apiClient;

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
        Pageable pageable = this.getPageableResponse(field, offset, pageSize);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);
        return departmentPage.map(department -> modelMapper.map(department, DepartmentDTO.class));
    }

    @Override
    public Page<EmployeeDTO> getEmployeeListWithDepartmentName(String departmentName, Integer offset, Integer pageSize, String field) {
        Department departmentObject = departmentRepository.findByDepartmentName(departmentName);
        String departmentCode = departmentObject.getDepartmentCode();
        return apiClient.getEmployeeListByDepartmentIdWithPaginationAndSorting(departmentCode, offset, pageSize, field);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) throws IllegalAccessException, NoSuchFieldException {
        Department department = departmentRepository.findById(id).get();
        Field[] fields = DepartmentDTO.class.getFields();
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
    public String deleteDepartment(Long id) {
         departmentRepository.deleteById(id);
         return id+" THIS DEPARTMENT SUCCESSFULLY DELETED...!";
    }


    @Override
    public List<EmployeeDTO> getEmployeeListWithDepartmentNameExample1(String departmentName, Integer offset, Integer pageSize, String field) {
        Department departmentObject = departmentRepository.findByDepartmentName(departmentName);
        String departmentCode = departmentObject.getDepartmentCode();
        String uri = "http://localhost:8080/api/employee/dept/" + departmentCode;
        Flux<EmployeeDTO> employeeDTOFlux = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(EmployeeDTO.class);
        Mono<List<EmployeeDTO>> employeesDTOMono = employeeDTOFlux.collectList();
        return employeesDTOMono.block();
    }

    private Pageable getPageableResponse(String field, Integer offset, Integer pageSize) {
        int validatedOffset = offset != null ? offset : DefaultValues.DEFAULT_OFFSET;
        int validatedPageSize = pageSize != null ? pageSize : DefaultValues.DEFAULT_PAGE_SIZE;
        String validatedField = (field == null || field.isEmpty() || field.isBlank()) ? DefaultValues.DEFAULT_FIELD : field;
        return PageRequest.of(validatedOffset, validatedPageSize, Sort.by(Sort.Direction.ASC, validatedField));
    }


}
