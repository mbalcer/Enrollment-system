export interface IAppointment {
  startTime: string;
  endTime: string;
}

export class Appointment implements IAppointment {
  startTime: string;
  endTime: string;

  constructor() {
    this.startTime = '';
    this.endTime = '';
  }
}
