import { Component, OnInit } from '@angular/core';
import { Patient } from '../models/patient';
import { ActivatedRoute } from '@angular/router';
import { PatientService } from '../services/patient.service';
import { Note } from '../models/note';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.sass']
})
export class PatientDetailComponent implements OnInit {
  patient: Patient | undefined;
  notes: Note[] = [];

  constructor(private route: ActivatedRoute, private patientService: PatientService, private noteService: NoteService) { 
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.patientService.findById(+id).subscribe((data: Patient) => {
        this.patient = data;
      });

      this.noteService.findNotesByPatientId(+id).subscribe((data: Note[]) => {
        this.notes = data;
      })
    }
  }
}
