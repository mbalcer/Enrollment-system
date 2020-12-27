export interface ISubject {
  id: number;
  name: string;
  description: string;
  numberOfHours: number;
  courseType: string;
  ects: number;
  language: string;
}

export class Subject implements ISubject {
  id: number;
  name: string;
  description: string;
  numberOfHours: number;
  courseType: string;
  ects: number;
  language: string;

  constructor() {
    this.id = null;
    this.name = '';
    this.description = '';
    this.numberOfHours = 0;
    this.courseType = '';
    this.ects = 0;
    this.language = '';
  }
}
