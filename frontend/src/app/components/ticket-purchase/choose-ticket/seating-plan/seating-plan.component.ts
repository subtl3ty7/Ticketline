import {Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import * as d3 from 'd3';
import {Section} from '../../../../dtos/section';
import {Seat} from '../../../../dtos/seat';
import {Show} from '../../../../dtos/show';


@Component({
  selector: 'app-seating-plan',
  templateUrl: './seating-plan.component.html',
  styleUrls: ['./seating-plan.component.css']
})
export class SeatingPlanComponent implements OnInit {

  stageSizeX: number;
  stageSizeY: number;
  seatWidth: number;
  seatHeight: number;
  seatXDistance: number;
  seatYDistance: number;
  sectionDistance: number;
  sectionXOffset: number;
  sectionYOffset: number;
  @Input() show: Show;
  @Input() sections: Section[];
  @Input() selectedSeats: Seat[];
  sectionGroups: any[];
  host;
  svg;
  g;
  x;
  y;
  constructor(private elRef: ElementRef) {
  this.host = this.elRef.nativeElement;
}

  ngOnInit(): void {
    this.setSeatingPlanDimensions();
    this.createSeatingPlan(this.sections.length);
  }


  private createSeatingPlan(layout: number) {
    this.setSectorAndSeatInfo();
    this.createStage();
    this.createSections(layout);
    this.createSeats(layout);
  }


  private setSectorAndSeatInfo() {
    this.seatHeight  = 20;
    this.seatWidth = 20;
    this.seatXDistance = 10;
    this.seatYDistance = 20;
    this.sectionDistance = 50;
    this.sectionYOffset = 50;
    this.sectionXOffset = 10;

  }

  private setSeatingPlanDimensions() {
    const height = 600;
    const width = 600;
    this.svg = d3.select(this.host).append('svg')
      .attr('width', width)
      .attr('height', height)
      .attr('viewBox', '0 0 ' + width + ' ' + height);
    this.g = this.svg.append('g')
      .attr('transform', 'translate(0,0)');
  }

  private createStage() {
    this.stageSizeX = 200;
    this.stageSizeY = 70;
    const stage = this.g.append('rect')
      .attr('x', 200)
      .attr('y', 0)
      .attr('width', this.stageSizeX)
      .attr('height', this.stageSizeY)
      .attr('fill-opacity', 0.1);
    const text = this.g.append('text')
      .attr('x', 255)
      .attr('y', 45)
      .attr('font-size', '32px')
      .text('Stage');
  }

  private createSections(layout: number) {
    let i;
    let j;
    let row;
    const sectionSizeX = 160;
    let sectionSizeY;
    let column = 3;
    if (layout === 3) {
      row = 1;
    } else {
      row = 2;
    }
    this.sectionGroups = [];
    let currentSection = 0;
    for (i = 0; i < row; i++) {
      j = 0;
      if (i > 0 ) {
        if (layout === 4) {
          j = 1;
          column = 2;
        }
      }

      for (j; j < column; j++) {
        if (layout === 5 && j === 1 && i === 1) {
          continue;
        }
      const section = this.g.append('g');
      let x;
      // set section X position
      if (j === 0) {
        x = this.sectionXOffset;
      } else {
        x = (sectionSizeX + this.sectionDistance) * j + this.sectionXOffset;
      }
      let y;
      const horizontalSeatAmount = 5;
      const verticalSeatAmount = this.sections[currentSection].seats.length / horizontalSeatAmount;
      sectionSizeY = (verticalSeatAmount * this.seatHeight) + (verticalSeatAmount * this.seatYDistance) + this.seatYDistance;
        // set section Y position
      if (j === 1) {
        y = (this.stageSizeY + this.sectionYOffset - (this.sectionYOffset * i)) * (i + 1) + (sectionSizeY * i);
      } else {
        y = (sectionSizeY + this.sectionDistance) * i + this.sectionYOffset;
      }
      section.append('rect')
        .attr('x', x)
        .attr('y', y)
        .attr('width', sectionSizeX)
        .attr('height', sectionSizeY)
        .attr('fill-opacity', 0.1)
        .attr('id', 's' + currentSection);
      this.sectionGroups.push(section);
      currentSection++;
    }
    }
  }

  private createSeats(layout: number) {
    let currentSectionIndex;
    let currentSeatIndex;
    let currentRow;
    let currentCol;
    const sectionAmount = layout;
    const colMax = 5;
    // For each section
    for (currentSectionIndex = 0; currentSectionIndex < sectionAmount; currentSectionIndex++) {
      const section = this.sectionGroups[currentSectionIndex];
      const seatAmount = this.sections[currentSectionIndex].seats.length;
      currentSeatIndex = 0;
      const rowMax = seatAmount / colMax;
        for (currentRow = 0; currentRow < rowMax; currentRow++) {
          for (currentCol = 0; currentCol < colMax; currentCol++) {
            const seatObject = this.sections[currentSectionIndex].seats[currentSeatIndex];
            const isSeatFree = this.isSeatFree(seatObject);
            let color;
            if (isSeatFree) {
              color = 'lemonchiffon';
            } else {
              color = 'gray';
            }
            let x;
            if (currentCol === 0) {
              x = +section.select('#s' + currentSectionIndex).attr('x') + (this.seatXDistance);
            } else {
              // tslint:disable-next-line:max-line-length
              x = +section.select('#s' + currentSectionIndex).attr('x') + ((this.seatXDistance + this.seatWidth) * currentCol) + this.seatXDistance;
            }

            let y;
            if (currentRow === 0) {
              y = +section.select('#s' + currentSectionIndex).attr('y') + (this.seatYDistance);
            } else {
              // tslint:disable-next-line:max-line-length
              y = +section.select('#s' + currentSectionIndex).attr('y') + ((this.seatYDistance + this.seatHeight) * currentRow) + this.seatYDistance;
            }
            const selected = this.selectedSeats;
            const seat = this.sectionGroups[currentSectionIndex].append('rect')
              .attr('x', x)
              .attr('y', y)
              .attr('width', this.seatWidth)
              .attr('height', this.seatHeight)
              .attr('sectionId', seatObject.sectionId)
              .attr('rowCol', seatObject.seatRow + seatObject.seatColumn)
              .attr('fill',  color)
              .attr('sectionName', this.sections[currentSectionIndex].name)
              .on('click', function() {
                if (isSeatFree) {
                  if (!selected.includes(seatObject)) {
                    d3.select(this)
                      .attr('fill', 'green');
                    selected.push(seatObject);
                  } else {
                    d3.select(this)
                      .attr('fill', color);
                    const index = selected.indexOf(seatObject);
                    selected.splice(index, 1);
                  }
                }
              })
              .on('mouseover', function() {
                if (isSeatFree && !selected.includes(seatObject)) {
                  d3.select(this)
                    .attr('fill', 'blue');
                }
              })
              .on('mouseout', function() {
                if (isSeatFree && !selected.includes(seatObject)) {
                  d3.select(this)
                    .attr('fill', color);
                }
              });
            currentSeatIndex++;
          }
        }
    }
  }

  isSeatFree(seat: Seat): boolean {
    return (this.show.takenSeats.filter(s => s.id === seat.id).length === 0);
  }
}
