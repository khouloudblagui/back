import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RoomService } from './room.service';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Room } from './room.model';
import { DataSource } from '@angular/cdk/collections';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { FormDialogComponent } from './dialog/form-dialog/form-dialog.component';
import { DeleteDialogComponent } from './dialog/delete/delete.component';
import { BehaviorSubject, fromEvent, merge, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SelectionModel } from '@angular/cdk/collections';
import { Direction } from '@angular/cdk/bidi';
import { UnsubscribeOnDestroyAdapter } from '@shared';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-allroom',
  templateUrl: './allroom.component.html',
  styleUrls: ['./allroom.component.scss'],
})
export class AllroomComponent extends UnsubscribeOnDestroyAdapter implements OnInit {
  displayedColumns = [
    'select',
    'img',
    'roomNumber',
    'patientName',
    'roomType',
    'gender',
    'admitDate',
    'dischargeDate',
    'actions',
  ];
  exampleDatabase?: RoomService;
  dataSource!: ExampleDataSource;
  selection = new SelectionModel<Room>(true, []);
  index?: number;
  id?: number;
  room?: Room;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public roomService: RoomService,
    private snackBar: MatSnackBar
  ) {
    super();
  }

  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild('filter', { static: true }) filter?: ElementRef;

  ngOnInit() {
    this.loadData();
  }

  refresh() {
    this.loadData();
  }

  addNew() {
    let tempDirection: Direction;
    if (localStorage.getItem('isRtl') === 'true') {
      tempDirection = 'rtl';
    } else {
      tempDirection = 'ltr';
    }
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        room: this.room,
        action: 'add',
      },
      direction: tempDirection,
    });
    this.subs.sink = dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.exampleDatabase?.dataChange.value.unshift(this.roomService.getDialogData());
        this.refreshTable();
        this.showNotification('snackbar-success', 'Add Record Successfully...!!!', 'bottom', 'center');
      }
    });
  }

  editCall(row: Room) {
    this.id = row.id;
    let tempDirection: Direction;
    if (localStorage.getItem('isRtl') === 'true') {
      tempDirection = 'rtl';
    } else {
      tempDirection = 'ltr';
    }
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        room: row,
        action: 'edit',
      },
      direction: tempDirection,
    });
    this.subs.sink = dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase?.dataChange.value.findIndex((x) => x.id === this.id);
        if (foundIndex != null && this.exampleDatabase) {
          this.exampleDatabase.dataChange.value[foundIndex] = this.roomService.getDialogData();
          this.refreshTable();
          this.showNotification('black', 'Edit Record Successfully...!!!', 'bottom', 'center');
        }
      }
    });
  }

  deleteItem(row: Room) {
    this.id = row.id;
    let tempDirection: Direction;
    if (localStorage.getItem('isRtl') === 'true') {
      tempDirection = 'rtl';
    } else {
      tempDirection = 'ltr';
    }
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: row,
      direction: tempDirection,
    });
    this.subs.sink = dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase?.dataChange.value.findIndex((x) => x.id === this.id);
        if (foundIndex != null && this.exampleDatabase) {
          this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
          this.refreshTable();
          this.showNotification('snackbar-danger', 'Delete Record Successfully...!!!', 'bottom', 'center');
        }
      }
    });
  }

  private refreshTable() {
    this.paginator._changePageSize(this.paginator.pageSize);
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.renderedData.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ? this.selection.clear() : this.dataSource.renderedData.forEach((row: Room) => this.selection.select(row));
  }

  removeSelectedRows() {
    const totalSelect = this.selection.selected.length;
    this.selection.selected.forEach((item) => {
      const index: number = this.dataSource.renderedData.findIndex((d) => d === item);
      this.exampleDatabase?.dataChange.value.splice(index, 1);
      this.refreshTable();
      this.selection = new SelectionModel<Room>(true, []);
    });
    this.showNotification('snackbar-danger', totalSelect + ' Record Delete Successfully...!!!', 'bottom', 'center');
  }

  public loadData() {
    this.exampleDatabase = new RoomService(this.httpClient);
    this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator, this.sort);
    this.subs.sink = fromEvent(this.filter?.nativeElement, 'keyup').subscribe(() => {
      if (!this.dataSource) {
        return;
      }
      this.dataSource.filter = this.filter?.nativeElement.value;
    });
  }

  showNotification(
    colorName: string,
    text: string,
    placementFrom: MatSnackBarVerticalPosition,
    placementAlign: MatSnackBarHorizontalPosition
  ) {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }
}
export class ExampleDataSource extends DataSource<Room> {
  private dataChange = new BehaviorSubject<Room[]>([]);
  get data(): Room[] { return this.dataChange.value; }

  // Ajoutez ces propriétés
  filteredData: Room[] = [];
  renderedData: Room[] = [];
  filterChange = new BehaviorSubject('');

  get filter(): string {
    return this.filterChange.value;
  }

  set filter(filter: string) {
    this.filterChange.next(filter);
  }

  constructor(private roomService: RoomService, private paginator: MatPaginator, private sort: MatSort) {
    super();
    this.filterChange.subscribe(() => this.paginator.pageIndex = 0);
  }

  connect(): Observable<Room[]> {
    const displayDataChanges = [
      this.dataChange,
      this.paginator.page,
      this.sort.sortChange,
      this.filterChange
    ];

    return merge(...displayDataChanges).pipe(
      map(() => {
        if (!this.roomService) {
          return [];
        }

        this.filteredData = this.roomService.data.slice().filter((room: Room) => {
          const searchStr = (room.roomNumber + room.patientName + room.roomType + room.gender).toLowerCase();
          return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
        });

        const sortedData = this.sortData(this.filteredData.slice());
        const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
        this.renderedData = sortedData.splice(startIndex, this.paginator.pageSize);
        return this.renderedData;
      })
    );
  }

  disconnect() {}

  refreshData() {
    this.roomService.getAllRooms(); // Assuming you have a method to fetch rooms
  }

  sortData(data: Room[]): Room[] {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'roomNumber': return compare(a.roomNumber, b.roomNumber, isAsc);
        case 'patientName': return compare(a.patientName, b.patientName, isAsc);
        case 'roomType': return compare(a.roomType, b.roomType, isAsc);
        case 'gender': return compare(a.gender, b.gender, isAsc);
        case 'admitDate': return compare(a.admitDate, b.admitDate, isAsc);
        case 'dischargeDate': return compare(a.dischargeDate, b.dischargeDate, isAsc);
        default: return 0;
      }
    });
  }
}

// Fonction utilitaire pour comparer les valeurs lors du tri
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

