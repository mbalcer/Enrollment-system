import {Pipe, PipeTransform} from '@angular/core';
import {FieldOfStudyPipe} from './field-of-study.pipe';
import {SubjectGroup} from '../dashboard/components/groups/subject-group.model';

@Pipe({
  name: 'listFieldsOfStudy'
})
export class ListFieldsOfStudyPipe implements PipeTransform {

  transform(value: SubjectGroup): string {
    let list = '';
    value.fieldsOfStudyDTO.forEach(fieldOfStudy => {
      list += new FieldOfStudyPipe().transform(fieldOfStudy) + '\n';
    });
    return list;
  }

}
