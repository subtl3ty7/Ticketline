import {News} from '../../../../../../dtos/news';

export enum State {
  READY,
  EDIT,
  PROCESSING
}

export class NewsDetailsWrapper {
  private _model: News = new News();
  _state: State;

  get model(): News {
    return this._model;
  }

  set model(value: News) {
    this._model = value;
  }

  get state(): State {
    return this._state;
  }

  set state(value: State) {
    this._state = value;
  }
}
