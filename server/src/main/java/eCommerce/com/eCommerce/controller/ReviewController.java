package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.ReviewRequest;
import eCommerce.com.eCommerce.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/save-review")
    private ResponseEntity<String> saveReview(@RequestBody @Valid ReviewRequest request, Authentication authentication){
        return new ResponseEntity<>(reviewService.saveReview(request, authentication), HttpStatus.CREATED); //todo: to continue making the saveReview method in the service

    }

}
