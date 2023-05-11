import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RestaurantService } from '../service/restaurant.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Review } from '../models/Review';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit, OnDestroy {
  cuisine!: string
  restaurant!: string
  param$!: Subscription
  reviewForm!: FormGroup
  selectedFile!: File;
  imageData = "";
  blob!: Blob;

  constructor(private activatedRoute: ActivatedRoute, private restaurantSvc: RestaurantService,
              private fb: FormBuilder, private router: Router) { }
  
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.cuisine = params['cuisine'];
        this.restaurant = params['restaurant'];
        this.reviewForm = this.createForm();
      }
    );
  }

  createForm() {
    return this.fb.group({
      restaurantName: this.fb.control({value: this.restaurant, disabled: true}, Validators.required),
      reviewer: this.fb.control('', [ Validators.required, Validators.minLength(3) ] ),
      rating: this.fb.control('', [ Validators.required ] ),
      review: this.fb.control('', [ Validators.required ])
    }); 
  }

  hasError(input: string): boolean {
    return !!(this.reviewForm.get(input)?.invalid && this.reviewForm.get(input)?.dirty)
  }

  postReview() {
    const formVal = {...this.reviewForm.value, restaurantName: this.restaurant};
    // Convert selected file into a Blob
    if (this.selectedFile) {
      const fileReader = new FileReader();
      fileReader.readAsDataURL(this.selectedFile);
      fileReader.onloadend = () => {
        this.imageData = fileReader.result as string;
        const byteString = atob(this.imageData.split(',')[1]);
        const ab = new ArrayBuffer(byteString.length);
        const ia = new Uint8Array(ab);
        for (let i = 0; i < byteString.length; i++) {
          ia[i] = byteString.charCodeAt(i);
        }
        this.blob = new Blob([ab], { type: this.selectedFile.type });
        // service method takes in form values and uploaded image as separate parameters
        this.restaurantSvc.uploadReviewWithImage(formVal, this.blob)
          .then(
          response => console.log('Review saved successfully', response),
          error => console.log('Error while saving review', error)
        );
      };
    } else {
      this.restaurantSvc.uploadReview(formVal)
      .then(
        response => console.log('Review saved successfully', response),
        error => console.log('Error while saving review', error)
      );
    }
    this.router.navigate(['/list', this.cuisine]);
    }
  


  onFileSelected(event: Event) {
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    this.selectedFile = file; // retrieves the selected file and stores it in the selectedFile property
  }

  ngOnDestroy(): void { this.param$.unsubscribe(); }
}
