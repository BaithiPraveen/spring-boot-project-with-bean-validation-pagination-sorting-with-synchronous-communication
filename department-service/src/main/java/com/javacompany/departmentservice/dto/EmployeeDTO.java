package com.javacompany.departmentservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "firstName field is required..!")
    private String firstName;

    @NotNull(message = "lastName field is required..!")
    private String lastName;

    @Email(message = "required valid email..!")
    @NotNull(message = "email field is required..!")
    private String email;

    @NotNull(message = "departmentId field is required..!")
    private String departmentId;
}
