import {
  AfterViewInit,
  Component,
  Input,
  OnInit,
  ViewChild,
} from "@angular/core";
import { MatPaginator, MatSort, MatTableDataSource } from "@angular/material";
import { merge } from "rxjs";
import { User } from "./User";
import { UserService } from "./user.service";

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["../app.component.css", "./users.component.css"],
})
export class UsersComponent implements AfterViewInit, OnInit {
  isLoading = false;
  @Input() lastName: string;
  @Input() age: string;
  ageSort: string;
  nameSort: string;
  totalRows = 0;
  pageSize = 100;
  currentPage = 0;
  pageSizeOptions: number[] = [100, 200, 500];

  displayedColumns: string[] = ["name", "age"];
  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private userService: UserService) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    // reset the paginator after sorting
    this.sort.sortChange.subscribe(() => {
      this.paginator.pageIndex = 0;
      this.nameSort = null;
      this.ageSort = null;
      if (this.sort.active === "age") {
        this.ageSort = this.sort.direction;
      }
      if (this.sort.active === "name") {
        this.nameSort = this.sort.direction;
      }
      this.currentPage = 0;
    });

    this.paginator.page.subscribe(() => {
      this.ageSort = null;
      this.nameSort = null;
      this.currentPage = this.paginator.pageIndex;
      this.pageSize = this.paginator.pageSize;
    });

    // on sort or paginate events, load a new page
    merge(this.sort.sortChange, this.paginator.page).subscribe(() => {
      this.loadData();
    });
  }

  ngOnInit(): void {
    this.pageSize = 100;
    this.loadData();
  }

  filter() {
    this.currentPage = 0;
    this.loadData();
  }

  loadData() {
    this.isLoading = true;
    this.userService
      .fetchAll(
        this.lastName,
        this.age,
        this.currentPage,
        this.pageSize,
        this.nameSort,
        this.ageSort
      )
      .subscribe(
        (data: any) => {
          this.dataSource = new MatTableDataSource<any>(data.users);
          setTimeout(() => {
            this.paginator.pageIndex = this.currentPage;
            this.paginator.length = data.total;
            console.log(this.paginator.pageIndex);
            console.log(this.paginator.length);
          });
          this.dataSource._updateChangeSubscription();
          this.isLoading = false;
        },
        (error) => {
          console.log(error);
          this.isLoading = false;
        }
      );
  }
}
