package com.javacompany.employeeservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "firstName field is required..!")
    @Length(min = 3, message = "departmentCode length attlist 2 characters ..!")
    private String firstName;

    @NotNull(message = "lastName field is required..!")
    @Length(min = 2, message = "departmentCode length attlist 2 characters ..!")
    private String lastName;

    @Email(message = "required valid email..!", regexp = "^[A-Za-z][A-Za-z0-9_.]*[^_.][@][A-Za-z0-9]*[.]{1}[a-zA-Z]*$")
    @NotNull(message = "email field is required..!")
    private String email;

    @NotNull(message = "departmentId field is required..!")
    @Length(min = 3, message = "departmentCode length attlist 3 characters ..!")
    private String departmentId;
}
