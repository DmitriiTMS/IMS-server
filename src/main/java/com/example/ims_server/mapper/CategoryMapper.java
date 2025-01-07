package com.example.ims_server.mapper;

import com.example.ims_server.dtos.CategoryDTO;
import com.example.ims_server.dtos.UserDTO;
import com.example.ims_server.entitys.Category;
import com.example.ims_server.entitys.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDTO categoryDTO);
    CategoryDTO toCategoryDTO(Category category);
    List<CategoryDTO> toCategoryDTOList(List<Category> categories);

}