import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { AuthService, Role } from '@core';
@Component({
  selector: 'app-locked',
  templateUrl: './locked.component.html',
  styleUrls: ['./locked.component.scss'],
})
export class LockedComponent implements OnInit {
  authForm!: UntypedFormGroup;
  submitted = false;
  userImg: string | undefined;
  userFullName: string | undefined;
  hide = true;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      password: ['', Validators.required],
    });

    // Vérification que currentUserValue est défini avant d'accéder aux propriétés
    const currentUser = this.authService.currentUserValue;

    if (currentUser) {
      // Si l'image existe, l'utiliser, sinon utiliser une image par défaut
      this.userImg = currentUser.img || 'assets/images/default-user.png';  // Utilisez une image par défaut si nécessaire

      this.userFullName = `${currentUser.firstName || ''} ${currentUser.lastName || ''}`;
    } else {
      // Si currentUser n'est pas défini, rediriger vers la page de connexion
      this.router.navigate(['/authentication/signin']);
    }
  }

  get f() {
    return this.authForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.authForm.invalid) {
      return;
    }

    const currentUser = this.authService.currentUserValue;

    if (currentUser && currentUser.role) {
      const role = currentUser.role;

      // Redirection en fonction du rôle
      if (role === Role.Admin) {
        this.router.navigate(['/admin/dashboard/main']);
      } else if (role === Role.Doctor) {
        this.router.navigate(['/doctor/dashboard']);
      } else if (role === Role.Patient) {
        this.router.navigate(['/patient/dashboard']);
      } else {
        this.router.navigate(['/authentication/signin']);  // Redirection si le rôle est inconnu
      }
    } else {
      // Si l'utilisateur n'est pas défini ou n'a pas de rôle, rediriger vers la page de connexion
      this.router.navigate(['/authentication/signin']);
    }
  }
}





























/*export class LockedComponent implements OnInit {
  authForm!: UntypedFormGroup;
  submitted = false;
  userImg!: string;
  userFullName!: string;
  hide = true;
  constructor(
    private formBuilder: UntypedFormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    // constuctor
  }
  ngOnInit() {
    this.authForm = this.formBuilder.group({
      password: ['', Validators.required],
    });
    //this.userImg = this.authService.currentUserValue.img;
    this.userFullName =
      this.authService.currentUserValue.firstName +
      ' ' +
      this.authService.currentUserValue.lastName;
  }
  get f() {
    return this.authForm.controls;
  }
  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.authForm.invalid) {
      return;
    } else {
      const role = this.authService.currentUserValue.role;
      if (role === Role.All || role === Role.Admin) {
        this.router.navigate(['/admin/dashboard/main']);
      } else if (role === Role.Doctor) {
        this.router.navigate(['/doctor/dashboard']);
      } else if (role === Role.Patient) {
        this.router.navigate(['/patient/dashboard']);
      } else {
        this.router.navigate(['/authentication/signin']);
      }
    }
  }
}
*/
