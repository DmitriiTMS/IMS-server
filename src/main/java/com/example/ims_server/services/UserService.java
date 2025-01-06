package com.example.ims_server.services;

import com.example.ims_server.dtos.LoginRequest;
import com.example.ims_server.dtos.RegisterRequest;
import com.example.ims_server.dtos.Response;
import com.example.ims_server.dtos.UserDTO;
import com.example.ims_server.entitys.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserTransactions(Long id);
}
