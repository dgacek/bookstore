import {Component, OnInit} from '@angular/core';
import {BookService} from "../../services/rest/book.service";
import {BooksPage} from "../../types/BooksPage";

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {

  constructor(private bookService: BookService) {}

  books?: BooksPage;
  page: number = 0;
  size: number = 20;
  sort: string = "";
  search: string = "";

  ngOnInit() {
    this.refresh();
  }

  private refresh(): void {
    this.bookService.getAll(this.page, this.size, this.sort, this.search)
      .subscribe((result) => this.books = result);
  }

}
