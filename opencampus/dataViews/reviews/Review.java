package com.ss4.opencampus.dataViews.reviews;

import java.io.Serializable;

    /**
     * @author Morgan Smith
     * Review class to store Review object data
     * Getter and setter methods for Review properties
     **/
    public class Review implements Serializable {

        public String reviewDetails;

        /**
         * Constructs a Review with nothing given
         */
        public Review() {
        }

        /**
         * Constructs a Review with the given parameters
         * @param reviewDetails details of Review
         */
        public Review(String reviewDetails) {
            this.reviewDetails = reviewDetails;
        }

        /**
         * Gets the Details of the Review
         * @return reviewDetails
         */
        public String getReviewDetails() {
            return reviewDetails;
        }

        /**
         * Sets the Details of the Review to a new value
         * @param reviewDetails new id for the Review
         */
        public void setReviewDetails(String reviewDetails) {
            this.reviewDetails = reviewDetails;
    }
}
