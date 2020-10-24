export interface IFaculty {
  id: number;
  name: string;
  address: string;
  abbreviation: string;
}

export class Faculty implements IFaculty {
  id: number;
  name: string;
  address: string;
  abbreviation: string;

  constructor() {
    this.id = null;
    this.name = '';
    this.address = '';
    this.abbreviation = '';
  }
}
