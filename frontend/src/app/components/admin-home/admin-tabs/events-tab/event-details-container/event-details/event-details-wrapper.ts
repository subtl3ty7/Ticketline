import {DetailedEvent} from '../../../../../../dtos/detailed-event';

export enum State {
  READY,
  EDIT,
  PROCESSING
}

export class EventDetailsWrapper {
  private _model: DetailedEvent = new DetailedEvent();
  _state: State;

  get model(): DetailedEvent {
    return this._model;
  }

  set model(value: DetailedEvent) {
    this._model = value;
  }

  get state(): State {
    return this._state;
  }

  set state(value: State) {
    this._state = value;
  }
}
