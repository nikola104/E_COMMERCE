package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviewsByProductId(Long id);
}
