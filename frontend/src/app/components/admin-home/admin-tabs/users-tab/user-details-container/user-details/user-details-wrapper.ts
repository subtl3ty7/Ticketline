import {User} from '../../../../../../dtos/user';

export enum State {
  READY,
  PASSWORD,
  PROCESSING
}

export class UserDetailsWrapper {
  private _model: User = new User();
  _state: State;

  get model(): User {
    return this._model;
  }

  set model(value: User) {
    this._model = value;
  }

  get state(): State {
    return this._state;
  }

  set state(value: State) {
    this._state = value;
  }
}
