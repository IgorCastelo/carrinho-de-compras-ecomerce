package com.nerd.shopping_cart_backend.repository.request;

import com.nerd.shopping_cart_backend.model.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdaterequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
