export interface IUser {
  username?: string;
  fullName?: string;
  email?: string;
  password?: string;
}

export class User implements IUser {
  constructor(
    public username?: string,
    public email?: string,
    public password?: string,
    public fullName?: string,
  ) {}
}
