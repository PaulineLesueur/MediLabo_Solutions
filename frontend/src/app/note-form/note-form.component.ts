import { Component } from '@angular/core';
import { Patient } from '../models/patient';
import { PatientService } from '../services/patient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Note } from '../models/note';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-note-form',
  templateUrl: './note-form.component.html',
  styleUrls: ['./note-form.component.sass']
})
export class NoteFormComponent {
  patient: Patient | undefined;
  noteForm!: FormGroup;
  note: Note | undefined;

  constructor(
    private route: ActivatedRoute, 
    private patientService: PatientService,
    private formBuilder: FormBuilder,
    private noteService: NoteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.patientService.findById(+id).subscribe((data: Patient) => {
        this.patient = data;
        console.log(this.patient);
        this.initializeForm();
      });
    }
  }

  initializeForm() {
    this.noteForm = this.formBuilder.group({
      patId: [{ value: this.patient?.id, disabled: true }, Validators.required],
      patient: [{ value: `${this.patient?.lastName} ${this.patient?.firstName}`, disabled: true }, Validators.required],
      note: ['', Validators.required]
    })
  }

  onSubmit() {
    if(this.noteForm.invalid) {
      this.noteForm.markAllAsTouched();
      return;
    }

    const formData = this.noteForm.getRawValue();
    const newNote: Note = {
      patId: formData.patId,
      patient: formData.patient,
      note: formData.note
    };

    this.noteService.createNote(newNote).subscribe(() => {
      this.router.navigate([`/patient/`, this.patient?.id]);
    })
  }
}
