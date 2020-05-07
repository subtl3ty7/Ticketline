export class User {
  public id: number;
  public userCode: string;
  public firstName: string;
  public lastName: string;
  public email: string;
  public password: string;
  public birthday: Date;
  public createdAt: string;
  public updatedAt: string;
  public blocked: boolean;
  public logged: boolean;
  public admin: boolean;
  public points: number;
  public userType: string;
  private seenMessages: boolean;
  constructor() {
    this.id = null;
    this.userCode = null;
    this.firstName = '';
    this.lastName = '';
    this.birthday = null;
    this.createdAt = null;
    this.updatedAt = null;
    this.email = '';
    this.password = '';
    this.userType = '';
    this.admin = false;
    this.points = 0;
    this.logged = false;
    this.blocked = false;
  }




}
