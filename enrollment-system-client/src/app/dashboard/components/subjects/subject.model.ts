export interface ISubject {
  id: number;
  name: string;
  description: string;
  numberOfHours: number;
  courseType: string;
  ECTS: number;
}

export class Subject implements ISubject {
  id: number;
  name: string;
  description: string;
  numberOfHours: number;
  courseType: string;
  ECTS: number;

  constructor() {
    this.id = null;
    this.name = '';
    this.description = '';
    this.numberOfHours = 0;
    this.courseType = '';
    this.ECTS = 0;
  }
}
