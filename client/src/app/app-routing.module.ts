import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { ListComponent } from './components/list.component';
import { ReviewComponent } from './components/review.component';
import { ReviewsListComponent } from './components/reviews-list.component';

const routes: Routes = [
  { path: '', title: 'Search Restaurant', component: SearchComponent },
  { path: 'list/:cuisine', title: 'List of Restaurants', component: ListComponent },
  { path: 'leavereview/:cuisine/:restaurant', title: 'Leave a Review', component: ReviewComponent },
  { path: 'reviews/:cuisine/:restaurant', title: 'Reviews List', component: ReviewsListComponent },
  { path: '**', redirectTo: '',  pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
