package com.example.ims_server.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suppliers")
@Data
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя поставщика обязательно")
    private String name;

    @NotBlank(message = "Контактная информация обязательна")
    @Column(name = "contact_info")
    private String contactInfo;

    private String address;

    @OneToMany(mappedBy = "supplier")
    private List<Transaction> transactions;
}
