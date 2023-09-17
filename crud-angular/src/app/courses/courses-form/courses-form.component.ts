import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { CoursesService } from '../services/courses.service';

@Component({
  selector: 'app-courses-form',
  templateUrl: './courses-form.component.html',
  styleUrls: ['./courses-form.component.scss']
})
export class CoursesFormComponent implements OnInit {

  form: UntypedFormGroup;

  constructor(private formBuilder: UntypedFormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private service: CoursesService,
              private location: Location,
              private snackBar: MatSnackBar) {
    this.form = this.formBuilder.group({
      name: [null],
      category: [null]
    });
  }

  ngOnInit(): void {
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
