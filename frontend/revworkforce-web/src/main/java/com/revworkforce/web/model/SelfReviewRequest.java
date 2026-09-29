package com.revworkforce.web.model;

public class SelfReviewRequest {

    private String selfReview;

    public SelfReviewRequest() {
    }

    public SelfReviewRequest(String selfReview) {
        this.selfReview = selfReview;
    }

    public String getSelfReview() {
        return selfReview;
    }

    public void setSelfReview(String selfReview) {
        this.selfReview = selfReview;
    }
}