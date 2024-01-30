package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.ReviewRequest;
import eCommerce.com.eCommerce.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviewsByProductId(Long id);

    String saveReview(ReviewRequest request);
}
