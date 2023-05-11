import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { RestaurantService } from '../service/restaurant.service';
import { Restaurant } from '../models/Restaurant';
import { ReviewService } from '../service/review.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, OnDestroy {
  cuisine!: string
  param$!: Subscription
  restaurants!: Restaurant[];
  isLoading: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private restaurantSvc: RestaurantService,
              private reviewSvc: ReviewService) { }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.cuisine = params['cuisine'];
        this.isLoading = true; 
        this.restaurantSvc.getRestaurantsByCuisine(this.cuisine)
          .then(
            (r) => {
              const restaurants = r;
              this.restaurants = [];

              for (const restaurant of restaurants) {
                const r = new Restaurant (
                  restaurant.name,
                  restaurant.rating,
                  restaurant.url,
                  restaurant.address,
                  restaurant.address_line_2,
                  restaurant.cuisine,
                  restaurant.reviewCount
                );
                this.restaurants.push(r);
              }
            }
          )
          .catch (
            (error) => {
              console.error("Error fetching restaurants:", error);
              this.restaurants = []; 
            }
          )
          .finally(() => {
            this.isLoading = false;  
          });
      }
    )
    
  }

  ngOnDestroy(): void { this.param$.unsubscribe(); }

}
