package com.example.ims_server.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "NameLoginDTO обязательно")
    private String email;

    @NotBlank(message = "PasswordLoginDTO обязательно")
    private String password;
}
