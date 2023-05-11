import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { UploadResult } from '../models/upload-result';
import { Review } from '../models/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private UPLOAD_WITH_IMAGE_URL = "/review/uploadwimage";
  private UPLOAD_URL = "/review/upload";
  private FIND_REVIEW_URL = "/review/{restaurant}";

  constructor(private httpClient: HttpClient) { }

  uploadReviewWithImage(form: any, image: Blob) {
    const formData = new FormData();
    formData.set('restaurantName', form['restaurantName']);
    formData.set('reviewer', form['reviewer']);
    formData.set('rating', form['rating']);
    formData.set('review', form['review']);
    formData.set('imageFile', image);
    
    return firstValueFrom(this.httpClient.post<UploadResult>(this.UPLOAD_WITH_IMAGE_URL, formData));
    // above indicates that you're expecting the server to respond with data that can be assigned to a variable of type UploadResults.
    // It can also be a string or seomthing that you expect the server to return
  }

  uploadReview(form: any) {
    const formData = new FormData();
    formData.set('restaurantName', form['restaurantName']);
    formData.set('reviewer', form['reviewer']);
    formData.set('rating', form['rating']); // rating is sent over as string
    formData.set('review', form['review']);
    
    return firstValueFrom(this.httpClient.post<String>(this.UPLOAD_URL, formData));
  }

  getReviews(restaurant: string): Promise<Review[]> {
    const url = this.FIND_REVIEW_URL.replace("{restaurant}", restaurant);
    return firstValueFrom(this.httpClient.get<Review[]>(url));
  }


}
