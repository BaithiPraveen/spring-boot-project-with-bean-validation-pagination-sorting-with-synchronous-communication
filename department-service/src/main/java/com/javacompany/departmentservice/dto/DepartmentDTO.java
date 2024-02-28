package com.javacompany.departmentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    @NotNull(message = "departmentName field is required..!")
    private String departmentName;

    @NotNull(message = "departmentDescription field is required..!")
    private String departmentDescription;

    @NotNull(message = "departmentDescription field is required..!")
    private String departmentCode;
}
