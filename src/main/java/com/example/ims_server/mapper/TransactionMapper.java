package com.example.ims_server.mapper;

import com.example.ims_server.dtos.TransactionDTO;
import com.example.ims_server.entitys.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    // Маппинг одного объекта Transaction в TransactionDTO
    TransactionDTO toTransactionDTO(Transaction transaction);

    // Маппинг списка Transaction в список TransactionDTO
    List<TransactionDTO> toTransactionDTOList(List<Transaction> transactions);
}