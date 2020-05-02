export class User {
  constructor(
    public id: number,
    public user_code: string,
    public first_name: string,
    public last_name: string,
    public email: string,
    public password: string,
    public birthday: string,
    public created_at: string,
    public updated_at: string,
    public is_blocked: boolean,
    public is_logged: boolean,
    public points: number,
    public user_type: string,
    public seen_messages: boolean
  ) {
  }
}
