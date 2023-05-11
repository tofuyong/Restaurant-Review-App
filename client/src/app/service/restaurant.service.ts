import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Restaurant } from '../models/Restaurant';
import { UploadResult } from '../models/upload-result';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private FIND_RESTAURANT_URL = "/api/{cuisine}";
  private UPLOAD_WITH_IMAGE_URL = "/api/uploadwimage";
  private UPLOAD_URL = "/api/upload";

  constructor(private httpClient: HttpClient) { }

  getRestaurantsByCuisine(cuisine: string): Promise<any> {
    const url = this.FIND_RESTAURANT_URL.replace("{cuisine}", cuisine);
    return firstValueFrom(this.httpClient.get<Restaurant[]>(url));
  }

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

}
