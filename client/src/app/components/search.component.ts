import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CUISINES } from '../constants';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  form!: FormGroup;
  cuisines!: typeof CUISINES;

  constructor(private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
      this.form = this.createForm();
      this.cuisines = CUISINES;
  }

  createForm(): FormGroup {
    return this.fb.group({
      cuisine: this.fb.control('',)
    })
  }

  search() {
    const cuisine = this.form.value['cuisine'];
    this.router.navigate(['/list', cuisine]);
  }
}
