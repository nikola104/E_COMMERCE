package eCommerce.com.eCommerce.dto;

import eCommerce.com.eCommerce.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private String color;
    private String material;
    private String categoryName;
    private String subcategoryName;
    private String typeName;
    private byte[] imageData;
    private double rating;
    private List<Review> reviews;

}
