package com.javacompany.departmentservice.repository;

import com.javacompany.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String departmentCode);

    Optional<Department> findByDepartmentName(String departmentName);

    Boolean existsByDepartmentName(String departmentName);
}
