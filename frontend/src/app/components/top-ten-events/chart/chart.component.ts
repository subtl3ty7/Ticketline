import {Component, Input, OnInit} from '@angular/core';
import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';
import am4themes_animated from '@amcharts/amcharts4/themes/animated';
import {SimpleEvent} from '../../../dtos/simple-event';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  @Input() events: SimpleEvent[];
  chart;

  constructor() { }

  ngOnInit(): void {
    this.addChart();
  }


  private delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  public addChart() {
    /* Chart code */
    // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end

    const elementExists = document.getElementById('chartdiv');
    if (elementExists) {
      console.log('chartdiv exists.');
    } else {
      console.log('chartdiv doesnt exist.');
    }
    // Create chart instance
    this.chart = am4core.create('chartdiv', am4charts.PieChart);

    // Add data
    this.chart.data = [ {
      'country': 'Lithuania',
      'litres': 501.9
    }, {
      'country': 'Czechia',
      'litres': 301.9
    }, {
      'country': 'Ireland',
      'litres': 201.1
    }, {
      'country': 'Germany',
      'litres': 165.8
    }, {
      'country': 'Australia',
      'litres': 139.9
    }, {
      'country': 'Austria',
      'litres': 128.3
    }, {
      'country': 'UK',
      'litres': 99
    }, {
      'country': 'Belgium',
      'litres': 60
    }, {
      'country': 'The Netherlands',
      'litres': 50
    } ];

    // Add and configure Series
    const pieSeries = this.chart.series.push(new am4charts.PieSeries());
    pieSeries.dataFields.value = 'litres';
    pieSeries.dataFields.category = 'country';
    pieSeries.slices.template.stroke = am4core.color('#fff');
    pieSeries.slices.template.strokeWidth = 2;
    pieSeries.slices.template.strokeOpacity = 1;

// This creates initial animation
    pieSeries.hiddenState.properties.opacity = 1;
    pieSeries.hiddenState.properties.endAngle = -90;
    pieSeries.hiddenState.properties.startAngle = -90;
  }
}
