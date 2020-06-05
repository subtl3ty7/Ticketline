import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {Artist} from '../../../../../../../../dtos/artist';

@Component({
  selector: 'app-create-artists',
  templateUrl: './create-artists.component.html',
  styleUrls: ['./create-artists.component.css']
})
export class CreateArtistsComponent implements OnInit {
  public displayedColumns = [
    'firstName',
    'lastName',
    'delete'
  ];

  public dataSource: any;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('scrollBlock') scrollBlock: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  artist = new Artist();

  constructor(@Inject(MAT_DIALOG_DATA) public data: Artist[],
              public dialogRef: MatDialogRef<CreateArtistsComponent>,
              private router: Router) { }

  ngOnInit(): void {
    this.initTable();
  }

  private initTable() {
    if (this.data) {
      this.dataSource = new MatTableDataSource(this.data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  }
  public closeModal() {
    this.dialogRef.close();
  }

  createArtist() {
    if (this.artist && this.artist.firstName  && this.artist.lastName) {
      const artist = new Artist();
      artist.firstName = this.artist.firstName;
      artist.lastName = this.artist.lastName;
       this.data.push(artist);
       this.initTable();
    }
  }

  delete(artist) {
    const index = this.data.indexOf(artist, 0);
    if (index > -1) {
      this.data.splice(index, 1);
    }
    this.initTable();
  }
}
