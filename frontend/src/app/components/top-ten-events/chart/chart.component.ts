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
    am4core.useTheme(am4themes_animated);

    // Create chart instance
    this.chart = am4core.create('chartdiv', am4charts.PieChart3D);
    this.chart.innerRadius = am4core.percent(40);
    // this.chart.width = am4core.percent(100);
    // this.chart.height = am4core.percent(100);

    this.chart.data = [];

    for (const event of this.events) {
      this.chart.data.push({
        'event': event.name,
        'ticketsSold': event.totalTicketsSold
      });
    }

    // Add and configure Series
    const pieSeries = this.chart.series.push(new am4charts.PieSeries3D());

    pieSeries.dataFields.value = 'ticketsSold';
    pieSeries.dataFields.category = 'event';
    pieSeries.labels.template.fill = am4core.color('#fff');
    pieSeries.slices.template.stroke = am4core.color('#fff');
    pieSeries.slices.template.strokeWidth = 2;
    pieSeries.slices.template.strokeOpacity = 1;
    pieSeries.slices.template.fillOpacity = 1;
    pieSeries.ticks.template.hidden = true;
    pieSeries.labels.template.hidden = true;

    // This creates initial animation
    pieSeries.hiddenState.properties.opacity = 1;
    pieSeries.hiddenState.properties.endAngle = -90;
    pieSeries.hiddenState.properties.startAngle = -90;

    // create legend
    this.chart.legend = new am4charts.Legend();
    const legend = this.chart.legend;
    legend.parent = this.chart.chartContainer;
    legend.labels.template.text = '[bold white]{name}';
    legend.valueLabels.template.text = '[bold white]{value}';
    legend.position = 'left';
    legend.valign = 'top';
    legend.itemContainers.template.tooltipText = '{ticketsSold}';

    // custom marker
    const marker = legend.markers.template.children.getIndex(0);
    marker.cornerRadius(12, 12, 12, 12);
    marker.strokeWidth = 2;
    marker.strokeOpacity = 1;
    marker.stroke = am4core.color('#ccc');
  }
}
