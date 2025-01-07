package com.example.ims_server.mapper;


import com.example.ims_server.dtos.SupplierDTO;
import com.example.ims_server.entitys.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toSupplier(SupplierDTO supplierDTO);
    SupplierDTO toSupplierDTO(Supplier supplier);
    List<SupplierDTO> toSupplierDTOList(List<Supplier> suppliers);
}
