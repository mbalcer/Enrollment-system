import {Pipe, PipeTransform} from '@angular/core';
import {SubjectGroup} from '../dashboard/components/groups/subject-group.model';
import {DatePipe} from '@angular/common';

@Pipe({
  name: 'timeTable'
})
export class TimeTablePipe implements PipeTransform {

  transform(value: SubjectGroup): string {
    let list = '';
    const datePipe = new DatePipe('en-US');
    value.timeTableDTO.forEach(appointment => {
      list += datePipe.transform(appointment.startTime, 'yyyy-MM-dd, HH:mm') +
        ' - ' + datePipe.transform(appointment.endTime, 'HH:mm') + '\n';
    });
    return list;
  }

}
