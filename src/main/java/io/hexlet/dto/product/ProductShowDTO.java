package io.hexlet.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductShowDTO {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private Double rating;
    private boolean availability;
    private String category;
    private String image;
}
