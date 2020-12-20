import {StudyType} from '../../../model/enumeration/study-type.enum';
import {StudyMode} from '../../../model/enumeration/study-mode.enum';

export interface IFieldOfStudy {
  id: number;
  name: string;
  type: StudyType;
  mode: StudyMode;
  abbreviationFaculty: string;
}

export class FieldOfStudy implements IFieldOfStudy {
  id: number;
  name: string;
  type: StudyType;
  mode: StudyMode;
  abbreviationFaculty: string;

  constructor() {
    this.id = null;
    this.name = '';
    this.type = null;
    this.mode = null;
    this.abbreviationFaculty = '';
  }
}
