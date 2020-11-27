import {Pipe, PipeTransform} from '@angular/core';
import {IAppointment} from '../dashboard/components/groups/appointment.model';
import {DatePipe} from '@angular/common';

@Pipe({
  name: 'appointment'
})
export class AppointmentPipe implements PipeTransform {

  transform(value: IAppointment): string {
    const datePipe = new DatePipe('en-US');

    return datePipe.transform(value.startTime, 'yyyy-MM-dd, HH:mm') +
    ' - ' + datePipe.transform(value.endTime, 'HH:mm');
  }

}
