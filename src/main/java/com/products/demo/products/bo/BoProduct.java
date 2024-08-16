package com.products.demo.products.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "demo")
public class BoProduct {

    @Id
    private String id;
    private String name;
    private String type;
    private BigDecimal price;
    private byte[] image;
}
