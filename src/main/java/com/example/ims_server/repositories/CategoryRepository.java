package com.example.ims_server.repositories;

import com.example.ims_server.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
