import {News} from '../../../../../../dtos/news';


export class CreateNewsWrapper {
  private _model: News = new News();

  get model(): News {
    return this._model;
  }

  set model(value: News) {
    this._model = value;
  }
  valid() {
    return this.model.title && this.model.author && this.model.summary && this.model.text && this.model.photo;
  }
}
