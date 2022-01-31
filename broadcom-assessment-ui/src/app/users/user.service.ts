import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "./User";

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private http: HttpClient) {}

  fetchAll(
    lastName: string,
    age: string,
    currPageIndex: number,
    pageSize: number,
    nameSort: string,
    ageSort: string
  ): Observable<User[]> {
    let pageIndex = "0";
    let pageLength = "10";
    if (currPageIndex) {
      pageIndex = currPageIndex.toString();
    }
    if (pageSize) {
      pageLength = pageSize.toString();
    }
    if (lastName === undefined) lastName = "";
    if (age === undefined) age = "";
    if (nameSort === undefined) nameSort = "";
    if (ageSort === undefined) ageSort = "";
    return this.http.get<User[]>("http://localhost:8080/myapp/users", {
      params: {
        lastName: lastName,
        age: age,
        currPageIndex: pageIndex,
        pageSize: pageLength,
        nameSort: nameSort,
        ageSort: ageSort,
      },
    });
  }
}
