package com.javacompany.employeeservice.repository;

import com.javacompany.employeeservice.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDepartmentId(String departmentId, Pageable pageable);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);

    Page<Employee> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
}
