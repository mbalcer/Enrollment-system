export interface IFieldOfStudy {
  id: number;
  name: string;
  type: string;
  mode: string;
  abbreviationFaculty: string;
}

export class FieldOfStudy implements IFieldOfStudy {
  id: number;
  name: string;
  type: string;
  mode: string;
  abbreviationFaculty: string;

  constructor() {
    this.id = null;
    this.name = '';
    this.type = '';
    this.mode = '';
    this.abbreviationFaculty = '';
  }
}
