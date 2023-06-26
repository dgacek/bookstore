import {Pageable} from "./Pageable";
import {AuthorDTO} from "./AuthorDTO";

export interface AuthorsPage {
  content: AuthorDTO[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}
