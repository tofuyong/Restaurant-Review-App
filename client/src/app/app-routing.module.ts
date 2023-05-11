import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { ListComponent } from './components/list.component';
import { ReviewComponent } from './components/review.component';

const routes: Routes = [
  { path: '', component: SearchComponent },
  { path: 'list/:cuisine', component: ListComponent },
  { path: 'review/:cuisine/:restaurant', component: ReviewComponent },
  { path: '**', redirectTo: '',  pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
