import {Component, Input, OnInit} from '@angular/core';
import {News} from '../../../../../../../dtos/news';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';

@Component({
  selector: 'app-create-news-form',
  templateUrl: './create-news-form.component.html',
  styleUrls: ['./create-news-form.component.css']
})
export class CreateNewsFormComponent implements OnInit {
  @Input() news: News;
  photo;
  constructor() {
  }

  ngOnInit(): void {
  }

  inputChange(fileInputEvent: any) {
    this.photo = fileInputEvent.target.files[0];
    const rd = new FileReader();
    rd.onload = this.handleFile.bind(this);
    rd.readAsDataURL(this.photo);
  }

  handleFile(event) {
    this.news.photo = event.target.result;
  }

  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    this.news.stopsBeingRelevantAt = event.value;
  }
}
