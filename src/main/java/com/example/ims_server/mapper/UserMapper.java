package com.example.ims_server.mapper;


import com.example.ims_server.dtos.UserDTO;
import com.example.ims_server.entitys.User;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users); // Преобразование списка
}
