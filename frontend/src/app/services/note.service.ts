import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Note } from '../models/note';

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private notesApi = 'http://localhost:8080/notes';

  constructor(private http: HttpClient) { }

  public findNotesByPatientId(patId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.notesApi}/${patId}`);
  }

  public createNote(newNote: Note) {
    return this.http.post<Note>(`${this.notesApi}/create`, newNote);
  }
}
