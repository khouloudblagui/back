import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Appointments } from './appointments.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UnsubscribeOnDestroyAdapter } from '@shared';

@Injectable()
export class AppointmentsService extends UnsubscribeOnDestroyAdapter {
  private readonly API_URL = 'http://localhost:8087/api/doctor/appointments'; // L'URL de ton backend pour les rendez-vous
  isTblLoading = true;
  dataChange: BehaviorSubject<Appointments[]> = new BehaviorSubject<Appointments[]>([]);

  // Temporarily stores data from dialogs
  dialogData!: Appointments;

  constructor(private httpClient: HttpClient) {
    super();
  }

  get data(): Appointments[] {
    return this.dataChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }

  /** CRUD METHODS */
  // Méthode pour récupérer tous les rendez-vous d'un médecin spécifique
  getAppointmentsByDoctor(doctorId: number): void {
    this.subs.sink = this.httpClient
      .get<Appointments[]>(`${this.API_URL}/doctor/${doctorId}`) // Appel à l'API avec doctorId
      .subscribe({
        next: (data) => {
          this.isTblLoading = false;
          this.dataChange.next(data); // Met à jour les données
        },
        error: (error: HttpErrorResponse) => {
          this.isTblLoading = false;
          console.log('Error fetching appointments: ', error.message);
        },
      });
  }

  // Ajout d'un rendez-vous
  addAppointments(appointments: Appointments): void {
    this.dialogData = appointments;

    // Tu peux décommenter cette partie si tu veux envoyer les données à ton backend
    // this.httpClient.post(this.API_URL, appointments)
    //   .subscribe({
    //     next: (data) => {
    //       this.dialogData = appointments;
    //     },
    //     error: (error: HttpErrorResponse) => {
    //        // Gérer les erreurs ici
    //     },
    //   });
  }

  // Mise à jour d'un rendez-vous
  updateAppointments(appointments: Appointments): void {
    this.dialogData = appointments;

    // Tu peux décommenter cette partie si tu veux envoyer les modifications à ton backend
    // this.httpClient.put(`${this.API_URL}/${appointments.id}`, appointments)
    //   .subscribe({
    //     next: (data) => {
    //       this.dialogData = appointments;
    //     },
    //     error: (error: HttpErrorResponse) => {
    //        // Gérer les erreurs ici
    //     },
    //   });
  }

  // Suppression d'un rendez-vous
  deleteAppointments(id: number): void {
    console.log(id);

    // Tu peux décommenter cette partie si tu veux envoyer une requête de suppression à ton backend
    // this.httpClient.delete(`${this.API_URL}/${id}`)
    //   .subscribe({
    //     next: (data) => {
    //       console.log('Appointment deleted with ID: ', id);
    //     },
    //     error: (error: HttpErrorResponse) => {
    //        // Gérer les erreurs ici
    //     },
    //   });
  }
}















/*import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Appointments } from './appointments.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UnsubscribeOnDestroyAdapter } from '@shared';
@Injectable()
export class AppointmentsService extends UnsubscribeOnDestroyAdapter {
  private readonly API_URL = 'assets/data/doc-appointments.json';
  isTblLoading = true;
  dataChange: BehaviorSubject<Appointments[]> = new BehaviorSubject<
    Appointments[]
  >([]);
  // Temporarily stores data from dialogs
  dialogData!: Appointments;
  constructor(private httpClient: HttpClient) {
    super();
  }
  get data(): Appointments[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getAllAppointmentss(): void {
    this.subs.sink = this.httpClient
      .get<Appointments[]>(this.API_URL)
      .subscribe({
        next: (data) => {
          this.isTblLoading = false;
          this.dataChange.next(data);
        },
        error: (error: HttpErrorResponse) => {
          this.isTblLoading = false;
          console.log(error.name + ' ' + error.message);
        },
      });
  }
  addAppointments(appointments: Appointments): void {
    this.dialogData = appointments;

    // this.httpClient.post(this.API_URL, appointments)
    //   .subscribe({
    //     next: (data) => {
    //       this.dialogData = appointments;
    //     },
    //     error: (error: HttpErrorResponse) => {
    //        // error code here
    //     },
    //   });
  }
  updateAppointments(appointments: Appointments): void {
    this.dialogData = appointments;

    // this.httpClient.put(this.API_URL + appointments.id, appointments)
    //     .subscribe({
    //       next: (data) => {
    //         this.dialogData = appointments;
    //       },
    //       error: (error: HttpErrorResponse) => {
    //          // error code here
    //       },
    //     });
  }
  deleteAppointments(id: number): void {
    console.log(id);

    // this.httpClient.delete(this.API_URL + id)
    //     .subscribe({
    //       next: (data) => {
    //         console.log(id);
    //       },
    //       error: (error: HttpErrorResponse) => {
    //          // error code here
    //       },
    //     });
  }
}*/
