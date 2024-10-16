import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { UnsubscribeOnDestroyAdapter } from '@shared';
import { AuthService, Role } from '@core';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss'],
})
export class SigninComponent extends UnsubscribeOnDestroyAdapter implements OnInit {
  authForm!: UntypedFormGroup;
  submitted = false;
  loading = false;
  error = '';
  hide = true;
  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    super();
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      username: ['', Validators.required], // Champs vides
      password: ['', Validators.required], // Champs vides
    });
  }

  get f() {
    return this.authForm.controls;
  }

  // Méthodes pour effacer les champs
  adminSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  doctorSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  patientSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.error = '';

    if (this.authForm.invalid) {
      this.error = 'Email and Password not valid!';
      this.loading = false;
      return;
    }

    const email = this.f['username'].value;
    const password = this.f['password'].value;

    console.log('Tentative de connexion avec:', email, password);  // Log pour déboguer la connexion

    this.subs.sink = this.authService.login(email, password).subscribe({
      next: (res) => {
        // Récupérer le rôle depuis localStorage
        const role = localStorage.getItem('userRole');

        // Debugging pour voir la valeur récupérée du rôle
        console.log('Rôle récupéré du localStorage:', role);

        if (role) {
          // Logique de redirection selon le rôle sans changer la casse
          if (role === Role.Admin) {
            console.log('Redirection vers le dashboard Admin');
            this.router.navigate(['/admin/dashboard/main']);
          } else if (role === Role.Doctor) {
            console.log('Redirection vers le dashboard Doctor');
            this.router.navigate(['/doctor/dashboard']);
          } else if (role === Role.Patient) {
            console.log('Redirection vers le dashboard Patient');
            this.router.navigate(['/patient/dashboard']);
          } else {
            this.error = 'Role non reconnu'; // Gestion d'erreur si le rôle est inconnu
            console.error('Rôle non reconnu:', role);  // Log pour déboguer le rôle non reconnu
          }
        } else {
          this.error = 'Utilisateur ou rôle non défini';
          console.error('Erreur : rôle ou utilisateur non défini dans localStorage');  // Log si le rôle n'est pas trouvé
        }

        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur de connexion : ' + (error.error.message || error.message || error);
        this.submitted = false;
        this.loading = false;
        console.error('Erreur lors de la tentative de connexion:', error);  // Log en cas d'erreur de connexion
      },
    });
  }
}



























  /*constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    super();
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      username: ['', Validators.required], // Champs vides
      password: ['', Validators.required], // Champs vides
    });
  }

  get f() {
    return this.authForm.controls;
  }

  // Méthodes pour effacer les champs
  adminSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  doctorSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  patientSet() {
    this.authForm.get('username')?.setValue('');
    this.authForm.get('password')?.setValue('');
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.error = '';

    if (this.authForm.invalid) {
        this.error = 'Email and Password not valid!'; // Mise à jour du message d'erreur
        this.loading = false;
        return;
    }

    const email = this.f['username'].value; // Changez 'username' en 'email' si nécessaire
    const password = this.f['password'].value;

    this.subs.sink = this.authService.login(email, password).subscribe({
        next: (res) => {
            if (res) {
                const currentUser = this.authService.currentUserValue;
                // Logique de redirection selon le rôle
                const role = currentUser.role;
                if (role === Role.All || role === Role.Admin) {
                    this.router.navigate(['/admin/dashboard/main']);
                } else if (role === Role.Doctor) {
                    this.router.navigate(['/doctor/dashboard']);
                } else if (role === Role.Patient) {
                    this.router.navigate(['/patient/dashboard']);
                }
            } else {
                this.error = 'Invalid Login';
            }
            this.loading = false;
        },
        error: (error) => {
            this.error = error;
            this.submitted = false;
            this.loading = false;
        },
    });
}
}
  /*onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.error = '';

    if (this.authForm.invalid) {
      this.error = 'Username and Password not valid!';
      this.loading = false;
      return;
    }

    // Ensure form values are accessible and valid
    const username = this.f['username']?.value || '';
    const password = this.f['password']?.value || '';

    if (!username || !password) {
      this.error = 'Please provide both username and password!';
      this.loading = false;
      return;
    }

    this.subs.sink = this.authService
      .login(username, password) // Pass the username and password
      .subscribe({
        next: (res) => {
          if (res) {
            setTimeout(() => {
              const currentUser = this.authService.currentUserValue;
              if (currentUser) {
                const role = currentUser.role;
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
              this.loading = false;
            }, 1000);
          } else {
            this.error = 'Invalid Login';
            this.loading = false;
          }
        },
        error: (error) => {
          this.error = error;
          this.submitted = false;
          this.loading = false;
        },
      });
  }*/
/*import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { UnsubscribeOnDestroyAdapter } from '@shared';
import { AuthService, Role } from '@core';
@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss'],
})
export class SigninComponent
  extends UnsubscribeOnDestroyAdapter
  implements OnInit
{
  authForm!: UntypedFormGroup;
  submitted = false;
  loading = false;
  error = '';
  hide = true;
  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    super();
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      username: ['admin@hospital.org', Validators.required],
      password: ['admin@123', Validators.required],
    });
  }
  get f() {
    return this.authForm.controls;
  }
  adminSet() {
    this.authForm.get('username')?.setValue('admin@hospital.org');
    this.authForm.get('password')?.setValue('admin@123');
  }
  doctorSet() {
    this.authForm.get('username')?.setValue('doctor@hospital.org');
    this.authForm.get('password')?.setValue('doctor@123');
  }
  patientSet() {
    this.authForm.get('username')?.setValue('patient@hospital.org');
    this.authForm.get('password')?.setValue('patient@123');
  }
  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.error = '';
    if (this.authForm.invalid) {
      this.error = 'Username and Password not valid !';
      return;
    } else {
      this.subs.sink = this.authService
        .login(this.f['username'].value, this.f['password'].value)
        .subscribe({
          next: (res) => {
            if (res) {
              setTimeout(() => {
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
                this.loading = false;
              }, 1000);
            } else {
              this.error = 'Invalid Login';
            }
          },
          error: (error) => {
            this.error = error;
            this.submitted = false;
            this.loading = false;
          },
        });
    }
  }
}
*/
