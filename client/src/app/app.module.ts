import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SearchComponent } from './components/search.component';
import { ListComponent } from './components/list.component';
import { ReviewComponent } from './components/review.component';
import { ReviewsListComponent } from './components/reviews-list.component';


@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    ListComponent,
    ReviewComponent,
    ReviewsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
