package com.example.ims_server.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO {

    private Long id;

    @NotBlank(message = "Имя SupplierDTO обязательно к заполнению")
    private String name;

    @NotBlank(message = "Информация о контракте обязательна к заполнению")
    private String contactInfo;

    private String address;
}
