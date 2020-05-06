export class User {

  constructor(
    public id: number,
    public userCode: string,
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public birthday: Date,
    public createdAt: string,
    public updatedAt: string,
    public isBlocked: boolean,
    public isLogged: boolean,
    public isAdmin: boolean,
    public points: number,
    public userType: string,
    public seenMessages: boolean
  ) {
  }
}
