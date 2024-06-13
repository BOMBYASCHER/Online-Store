package io.hexlet.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private Long price;
    private Double rating;
    private boolean availability;
}
