export interface IFaculty {
  id: number;
  name: string;
  address: string;
  abbreviation: string;
  startRegistration: string;
}

export class Faculty implements IFaculty {
  id: number;
  name: string;
  address: string;
  abbreviation: string;
  startRegistration: string;

  constructor() {
    this.id = null;
    this.name = '';
    this.address = '';
    this.abbreviation = '';
    this.startRegistration = '';
  }
}
