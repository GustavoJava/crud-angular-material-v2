import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { CoursesService } from '../services/courses.service';
import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CourseResolver implements Resolve<Course> {

  constructor(private service: CoursesService){}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Course> {
    if(route.params && route.params['id']){
      const Id = route.params['id'];
      return this.service.loadById(Id);
    }

    const course:Course = {_id:'', name:'', category:''};
    return of(course);
  }
}
