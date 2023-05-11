import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Review } from '../models/Review';
import { ReviewService } from '../service/review.service';

@Component({
  selector: 'app-reviews-list',
  templateUrl: './reviews-list.component.html',
  styleUrls: ['./reviews-list.component.css']
})
export class ReviewsListComponent implements OnInit, OnDestroy {
  param$!: Subscription
  restaurant!: string
  cuisine!: string
  reviews!: Review[]
  isLoading: boolean = false;
  
  constructor(private activatedRoute: ActivatedRoute, private reviewSvc: ReviewService) { }
  
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.cuisine = params['cuisine'];
        this.restaurant = params['restaurant'];
      }
    )
    this.isLoading = true;
    this.reviewSvc.getReviews(this.restaurant)
    .then(
      (r) => {
        const reviews = r;
        this.reviews = [];

        for (const review of reviews) {
          const r = new Review (
            review.restaurantName,
            review.reviewer,
            review.rating,
            review.review,
            review.imageUrl
          );
          this.reviews.push(r);
        }
      }
    )
    .catch (
      (error) => {
        console.error("Error fetching reviews:", error);
        this.reviews = []; 
      }
    )
    .finally(() => {
      this.isLoading = false;  
    });
  }

  ngOnDestroy(): void { this.param$.unsubscribe(); }
}
