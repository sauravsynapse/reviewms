package com.jobex.reviewms.reviews;

import java.util.List;

public interface ReviewService {
    List<Review> findAllByCompanyId(Long companyId);

    boolean createReview(Review review, Long companyId);

    Review findReviewByReviewId(Long reviewId);

    boolean updateReviewByReviewId(Review review, Long reviewId);

    boolean deleteReviewByReviewId(Long reviewId);
}
