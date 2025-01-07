package com.example.ims_server.mapper;

import com.example.ims_server.dtos.ProductDTO;
import com.example.ims_server.entitys.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductDTOList(List<Product> products);
}
