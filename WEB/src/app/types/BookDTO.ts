import { AuthorDTO } from './AuthorDTO';

export interface BookDTO {
  id: number;
  title: string;
  isbn: string;
  author: AuthorDTO;
}
