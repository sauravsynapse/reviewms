package com.jobex.reviewms.reviews.impl;

import com.jobex.reviewms.reviews.Review;
import com.jobex.reviewms.reviews.ReviewRepository;
import com.jobex.reviewms.reviews.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Review review, Long companyId) {
        if(companyId!=null && review!=null)
        {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Review findReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReviewByReviewId(Review updatedReview, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(reviewId!=null) {
            review.setTitle(updatedReview.getTitle());
            review.setRating(updatedReview.getRating());
            review.setDescription(updatedReview.getDescription());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null)
        {
            reviewRepository.delete(review);
            return true;
        }
        else
            return false;
    }
}
