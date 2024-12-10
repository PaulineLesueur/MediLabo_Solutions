export class Note {
    patId: number;
    patient: string;
    note: string;

    constructor(patId: number, patient: string, note: string) {
        this.patId = patId;
        this.patient = patient;
        this. note = note;
    }
}
