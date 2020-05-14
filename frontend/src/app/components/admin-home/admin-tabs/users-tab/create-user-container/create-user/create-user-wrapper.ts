import {User} from '../../../../../../dtos/user';

export class CreateUserWrapper {
  private _model: User = new User();

  get model(): User {
    return this._model;
  }

  set model(value: User) {
    this._model = value;
  }
  valid() {
    return this.model.firstName && this.model.lastName && this.model.birthday && this.model.email && this.model.password;
  }
}
