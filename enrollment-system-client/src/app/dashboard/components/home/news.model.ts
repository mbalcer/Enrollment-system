import {User} from '../../../user/model/user.model';

export interface INews {
  id: number,
  title: string,
  description: string,
  timeOfPublication: string,
  author: User
}

export class News implements INews {
  id: number;
  title: string;
  description: string;
  timeOfPublication: string;
  author: User;

  constructor() {
    this.id = null;
    this.title = '';
    this.description = '';
    this.timeOfPublication = '';
    this.author = new User();
  }
}
