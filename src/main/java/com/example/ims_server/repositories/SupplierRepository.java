package com.example.ims_server.repositories;

import com.example.ims_server.entitys.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
