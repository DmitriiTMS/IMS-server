package com.example.ims_server.dtos;

import com.example.ims_server.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "NameRegisterDTO обязательно")
    private String name;

    @NotBlank(message = "emailRegisterDTO обязательно")
    private String email;

    @NotBlank(message = "PasswordRegisterDTO обязательно")
    private String password;

    @NotBlank(message = "phoneNumberRegisterDTO обязательно")
    private String phoneNumber;

    private UserRole role;

}