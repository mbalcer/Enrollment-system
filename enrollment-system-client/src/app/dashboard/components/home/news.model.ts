export interface INews {
  id: number,
  title: string,
  description: string,
  timeOfPublication: string
}

export class News implements INews {
  id: number;
  title: string;
  description: string;
  timeOfPublication: string;

  constructor() {
    this.id = null;
    this.title = '';
    this.description = '';
    this.timeOfPublication = '';
  }
}
