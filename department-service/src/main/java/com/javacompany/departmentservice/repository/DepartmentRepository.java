package com.javacompany.departmentservice.repository;

import com.javacompany.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByDepartmentCode(String departmentCode);

    Department findByDepartmentName(String departmentName);
}
