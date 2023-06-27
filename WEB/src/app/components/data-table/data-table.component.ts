import {Component, OnInit} from '@angular/core';
import {BookService} from "../../services/rest/book.service";
import {BooksPage} from "../../types/BooksPage";
import {FormControl} from "@angular/forms";
import {debounceTime, switchMap} from "rxjs";
import {AuthorService} from "../../services/rest/author.service";
import {AuthorsPage} from "../../types/AuthorsPage";

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {

  constructor(private bookService: BookService, private authorService: AuthorService) {}

  protected books?: BooksPage;
  protected authors?: AuthorsPage;
  protected page = 0;
  protected size = 20;
  protected sort = "id,asc";
  protected search = "";
  protected editModeFlag = false;
  protected deleteModeFlag = false;
  protected addModeFlag = false;
  protected mouseoverId = 0;
  protected mouseoverTitle = "";
  protected mouseoverIsbn = "";
  protected mouseoverAuthorName = "";

  authorSearchControl = new FormControl();

  ngOnInit() {
    this.refresh();
    this.authorSearchControl.valueChanges.pipe(
      debounceTime(1000),
      switchMap(changedValue => this.authorService.getAll(0, 20, "id,asc", changedValue))
    ).subscribe(response => this.authors = response);
  }

  protected refresh(){
    this.bookService.getAll(this.page, this.size, this.sort, this.search)
      .subscribe((result) => this.books = result);
  }

  protected enableEditMode() {
    this.authorSearchControl.setValue(this.mouseoverAuthorName);
    this.editModeFlag = true;
  }

  protected enableAddMode() {
    this.authorSearchControl.setValue("");
    this.addModeFlag = true;
  }

  protected setMouseover(id: number, title: string, isbn: string, authorName: string) {
    if (!this.editModeFlag && !this.deleteModeFlag && !this.addModeFlag) {
      this.mouseoverId = id;
      this.mouseoverTitle = title;
      this.mouseoverIsbn = isbn;
      this.mouseoverAuthorName = authorName;
    }
  }

  protected updateSelected() {
    let authorId = this.authors?.content.filter(x => x.name === this.authorSearchControl.getRawValue())[0]?.id;
    if (authorId === undefined) {
      this.authorService.add({name: this.authorSearchControl.getRawValue()}).subscribe(response => {
        authorId = response.id;
        this.bookService.update({id: this.mouseoverId, isbn: this.mouseoverIsbn, title: this.mouseoverTitle, authorId: authorId}).subscribe(() => this.refresh());
      });
    } else {
      this.bookService.update({id: this.mouseoverId, isbn: this.mouseoverIsbn, title: this.mouseoverTitle, authorId: authorId}).subscribe(() => this.refresh());
    }
    this.editModeFlag = false;
  }

  protected deleteSelected() {
    this.bookService.delete(this.mouseoverId).subscribe(() => this.refresh());
    this.deleteModeFlag = false;
  }

  protected addNew() {
    let authorId = this.authors?.content.filter(x => x.name === this.authorSearchControl.getRawValue())[0]?.id;
    if (authorId === undefined) {
      this.authorService.add({name: this.authorSearchControl.getRawValue()}).subscribe(response => {
        authorId = response.id;
        this.bookService.add({isbn: this.mouseoverIsbn, title: this.mouseoverTitle, authorId: authorId}).subscribe(() => this.refresh());
      });
    } else {
      this.bookService.add({isbn: this.mouseoverIsbn, title: this.mouseoverTitle, authorId: authorId}).subscribe(() => this.refresh());
    }
    this.addModeFlag = false;
  }
}
