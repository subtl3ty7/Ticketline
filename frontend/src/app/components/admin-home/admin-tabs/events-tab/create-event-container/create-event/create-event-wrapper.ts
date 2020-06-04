import {DetailedEvent} from '../../../../../../dtos/detailed-event';

export class CreateEventWrapper {
  private _model: DetailedEvent = new DetailedEvent();

  get model(): DetailedEvent {
    return this._model;
  }

  set model(value: DetailedEvent) {
    this._model = value;
  }
  valid() {
    return this.model.shows.length > 0
      && this.model.artists.length > 0
      && this.model.type
      && this.model.category
      && this.model.photo
      && this.model.description
      && this.model.name;
  }
}
