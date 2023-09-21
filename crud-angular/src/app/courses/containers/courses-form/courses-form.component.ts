import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-courses-form',
  templateUrl: './courses-form.component.html',
  styleUrls: ['./courses-form.component.scss']
})
export class CoursesFormComponent implements OnInit {

  form = this.formBuilder.group({
    _id: [''],
    name: [''],
    category: ['']
  });

  constructor(private formBuilder: NonNullableFormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private service: CoursesService,
              private location: Location,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    // const Id = this.route.snapshot.paramMap.get('id');
    // console.log('params: ',Id);

    const course: Course = this.route.snapshot.data['course'];
    this.form.setValue({
      _id: course._id,
      name: course.name,
      category: course.category
    });
  }

  onSubmit(): void {
    const course = (this.form.value);
    this.service.save(course)
        .subscribe(response =>
          this.onSuccess(),
         error => {
          this.onError()
        });
  }

  private onError() {
    this.snackBar.open('Erro ao Salvar curso!', 'X', {
      duration: 5000
    });
  }

  onSuccess() {
    this.snackBar.open('Curso salvo com sucesso!', 'X', {
      duration: 5000
    });
    this.onCancel();
  }

  onCancel(): void {
   this.location.back();
  }

}
