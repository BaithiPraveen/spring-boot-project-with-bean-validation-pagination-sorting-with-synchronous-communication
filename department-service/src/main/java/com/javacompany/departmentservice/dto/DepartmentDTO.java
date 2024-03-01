package com.javacompany.departmentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    @NotNull(message = "departmentName field is required..!")
    @Length(min = 3, message = "departmentName length attlist 3 characters ..!")
    private String departmentName;

    @NotNull(message = "departmentDescription field is required..!")
    @Length(min = 3, message = "departmentDescription length attlist 3 characters ..!")
    private String departmentDescription;

    @NotNull(message = "departmentDescription field is required..!")
    @Length(min = 3, message = "departmentCode length attlist 3 characters ..!")
    private String departmentCode;
}
