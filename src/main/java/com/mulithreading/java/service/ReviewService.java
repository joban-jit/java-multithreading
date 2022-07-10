package com.mulithreading.java.service;

import static com.mulithreading.java.util.CommonUtil.delay;

import com.mulithreading.java.domain.Review;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
