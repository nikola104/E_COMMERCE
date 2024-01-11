package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.exception.ReviewNotFoundException;
import eCommerce.com.eCommerce.model.Review;
import eCommerce.com.eCommerce.repository.ReviewRepository;
import eCommerce.com.eCommerce.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllReviewsByProductId(Long id) {
        List<Review> reviews = reviewRepository.findAllByProductId(id);
        return reviews;
    }
}
