import {Pipe, PipeTransform} from '@angular/core';
import {SubjectGroup} from '../dashboard/components/groups/subject-group.model';
import {AppointmentPipe} from './appointment.pipe';

@Pipe({
  name: 'timeTable'
})
export class TimeTablePipe implements PipeTransform {

  transform(value: SubjectGroup): string {
    let list = '';
    const appointmentPipe = new AppointmentPipe();
    value.timeTableDTO.forEach(appointment => {
      list += appointmentPipe.transform(appointment) + '\n';
    });
    return list;
  }

}
