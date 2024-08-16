package com.products.demo.products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOProduct {


    @Id
    private String id;
    private String name;
    private String type;
    private BigDecimal price;
    private String image;
}
