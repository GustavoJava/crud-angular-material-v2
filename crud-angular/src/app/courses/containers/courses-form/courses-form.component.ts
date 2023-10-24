import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { Lesson } from '../../model/lesson';

@Component({
  selector: 'app-courses-form',
  templateUrl: './courses-form.component.html',
  styleUrls: ['./courses-form.component.scss']
})
export class CoursesFormComponent implements OnInit {

  form!: FormGroup;

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

    this.form = this.formBuilder.group({
      _id: [course._id],
      name: [course.name,[Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      category: [course.category,[Validators.required]],
      lessons: this.formBuilder.array(this.retrieveLessons(course))
    });

    console.log(this.form.value);

  }

  private retrieveLessons(course: Course ) {
    const lessons = [];
    if (course?.lessons) {
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)));
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  getLessonsFormArray() {
      return (<UntypedFormArray>this.form.get('lessons'))?.controls;
  }

  private createLesson(lesson: Lesson = {id:'', name: '', youtubeUrl: ''}) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name],
      youtubeUrl: [lesson.youtubeUrl]
    })
  }

  addNewLesson(){
    const lessons =  this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  removeLesson(index: number){
    const lessons =  this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  onSubmit(): void {
    const course = (this.form.value);
    this.service.save(course)
        .subscribe(response =>
          this.onSuccess(),
         error => {
          console.log(error);
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

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if (field?.hasError('required')) {
      return 'campo obrigatório';
    }

    if (field?.hasError('minlength')) {
      const requiredLength = field.errors ? field.errors['minlength']['requiredLength'] : 5;
      return `o campo precisa ter no mínimo ${requiredLength} caracteres`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength = field.errors ? field.errors['maxlength']['requiredLength'] : 100;
      return `tamanho máximo excedido de ${requiredLength} caracteres`;
    }

    return 'campo inválido';
  }

}
