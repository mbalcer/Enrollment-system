import {Pipe, PipeTransform} from '@angular/core';
import {FieldOfStudy} from '../dashboard/components/university/field-of-study.model';
import {StudyType} from '../model/enumeration/study-type.enum';
import {StudyMode} from '../model/enumeration/study-mode.enum';

@Pipe({
  name: 'fieldOfStudy'
})
export class FieldOfStudyPipe implements PipeTransform {

  transform(value: FieldOfStudy): string {
    return value.name + ' ' + StudyType[value.type] + ' ' + StudyMode[value.mode];
  }

}
