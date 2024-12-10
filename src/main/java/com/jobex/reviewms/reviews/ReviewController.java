package com.jobex.reviewms.reviews;

import com.jobex.reviewms.reviews.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;
    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> findAllReviews(@RequestParam Long companyId)
    {
        List<Review> reviewList = reviewService.findAllByCompanyId(companyId);
        if(!reviewList.isEmpty())
        {
            return new ResponseEntity<>(reviewList, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody Review review, @RequestParam Long companyId)
    {
        if(reviewService.createReview(review,companyId)) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity("Review Created Successfully", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity("Review not saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findReviewByReviewId(@PathVariable Long reviewId)
    {
        Review review = reviewService.findReviewByReviewId(reviewId);
        if(review!=null)
        {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewByReviewId(@RequestBody Review review, @PathVariable Long reviewId)
    {
        if(reviewService.updateReviewByReviewId(review, reviewId))
            return new ResponseEntity("Review Updated Successfully",HttpStatus.CREATED);
        return new ResponseEntity("Review not saved", HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewByReviewId(@PathVariable Long reviewId)
    {
        if(reviewService.deleteReviewByReviewId(reviewId))
            return new ResponseEntity("Review Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity("Review not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.findAllByCompanyId(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
