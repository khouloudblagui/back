import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { User } from '../models/user';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '@core/models/authentication-request';
import { AuthenticationResponse } from '@core/models/authentication-response';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient, private router: Router) {
    const storedUser = localStorage.getItem('currentUser');
    let parsedUser = null;

    try {
      parsedUser = storedUser ? JSON.parse(storedUser) : null;
    } catch (error) {
      console.error('Erreur lors du parsing de currentUser:', error);
      // En cas d'erreur, nous mettons parsedUser à null
      parsedUser = null;
    }

    this.currentUserSubject = new BehaviorSubject<any>(parsedUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  // URL spécifique pour l'authentification
  login(email: string, password: string): Observable<any> {
    return this.http.post<AuthenticationResponse>(`${environment.baseUrl}/authenticate`, { email, password })
      .pipe(
        map((response) => {
          if (response && response.accessToken) {
            // Stocker les tokens et le rôle dans le localStorage
            localStorage.setItem('accessToken', response.accessToken);
            localStorage.setItem('refreshToken', response.refreshToken);
            localStorage.setItem('userRole', response.role);  // Stocker le rôle de l'utilisateur

            // Mettre à jour `currentUserSubject` avec le rôle récupéré
            const userWithRole = { role: response.role };
            this.currentUserSubject.next(userWithRole);  // Mise à jour du sujet avec le rôle
          }
          return response;
        }),
        catchError((error) => {
          console.error('Erreur lors de la connexion:', error);
          return throwError(() => new Error(error.message || 'Erreur de connexion'));
        })
      );
  }

  // URL spécifique pour le refresh token
  refreshToken(): Observable<any> {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
      return throwError(() => new Error('Aucun refresh token trouvé'));
    }

    return this.http.post<any>(`${environment.baseUrl}/refresh-token`, { refreshToken })
      .pipe(
        map((response) => {
          if (response && response.accessToken) {
            // Mettre à jour le token d'accès
            localStorage.setItem('accessToken', response.accessToken);
          }
          return response;
        }),
        catchError((error) => {
          console.error('Erreur lors du rafraîchissement du token:', error);
          return throwError(() => new Error('Impossible de rafraîchir le token'));
        })
      );
  }

  logout(): void {
    // Supprimer les informations d'authentification du localStorage
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('accessToken');
  }
}































/*export class AuthService {
  private baseUrl = environment.baseUrl;
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser: Observable<User | null>;

  constructor(private router: Router, private http: HttpClient) {
    const storedUser = localStorage.getItem('currentUser');
    let parsedUser: User | null = null;

    try {
      parsedUser = storedUser ? JSON.parse(storedUser) : null;
    } catch (error) {
      console.error('Erreur lors du parsing de currentUser:', error);
    }

    this.currentUserSubject = new BehaviorSubject<User | null>(parsedUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  isUserAuthenticated(): boolean {
    if (localStorage.getItem('accessToken')) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

  login(email: string, password: string): Observable<AuthenticationResponse> {
    const authenticationRequest: AuthenticationRequest = { email, password }; // Créer l'objet de requête avec email
    const url = `${this.baseUrl}/authenticate`;
    return this.http.post<AuthenticationResponse>(url, authenticationRequest).pipe(
      map((response) => {
        this.setUserToken(response);
        return response;
      })
    );
  }

  logout(): void {
    // Supprimer les données de l'utilisateur du localStorage
    localStorage.removeItem('currentUser');
    localStorage.removeItem('accessToken');
    this.currentUserSubject.next(null); // Mettre à jour le sujet actuel
    this.router.navigate(['/login']); // Rediriger vers la page de connexion
  }


  private setUserToken(response: AuthenticationResponse): void {
    // Logic to store user data and token
    localStorage.setItem('currentUser', JSON.stringify(response.user));
    localStorage.setItem('accessToken', response.accessToken);
    this.currentUserSubject.next(response.user); // Mettez à jour le sujet actuel
  }
}


















































/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '@core/models/authentication-request';
import { AuthenticationResponse } from '@core/models/authentication-response';
import { Checkemail } from '@core/models/checkemail';
import { RegisterRequest } from '@core/models/register-request';
import { environment } from 'environments/environment';



@Injectable({
  providedIn: 'root',
})

export class AuthService {
  private baseUrl = environment.baseUrl;
  /*private baseUrl = environment.baseUrl + '/auth';
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser: Observable<User | null>;

  constructor(private router: Router, private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User | null>(
      JSON.parse(localStorage.getItem('currentUser') || 'null')
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  isUserAuthenticated(): boolean {
    if (localStorage.getItem('accessToken')) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

  login(email: string, password: string): Observable<AuthenticationResponse> {
    const authenticationRequest: AuthenticationRequest = { email, password }; // Créer l'objet de requête avec email
    const url = `${this.baseUrl}/authenticate`;
    return this.http.post<AuthenticationResponse>(url, authenticationRequest).pipe(
        map((response) => {
            this.setUserToken(response);
            return response;
        })
    );
}






  /* login(authenticationRequest: AuthenticationRequest, password: any): Observable<AuthenticationResponse> {
    const url = `${this.baseUrl}/authenticate`;
    return this.http.post<AuthenticationResponse>(url, authenticationRequest).pipe(
      map((response) => {
        this.setUserToken(response);
        return response;
      })
    );
  }

  register(registerRequest: RegisterRequest): Observable<Checkemail> {
    const url = `${this.baseUrl}/register`;
    return this.http.post<Checkemail>(url, registerRequest);
  }

  setUserToken(authenticationResponse: AuthenticationResponse): void {
    localStorage.setItem('accessToken', authenticationResponse.accessToken);
    localStorage.setItem('refreshToken', authenticationResponse.refreshToken);
    localStorage.setItem('currentUser', JSON.stringify(authenticationResponse.user));
    this.currentUserSubject.next(authenticationResponse.user);
  }

  logout(): Observable<boolean> {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
    return of(true); // Return an observable
  }
}*/



 /* logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }*/




/*
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('currentUser') || '{}')
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    return this.http
      .post<User>(`${environment.apiUrl}/authenticate`, {
        username,
        password,
      })
      .pipe(
        map((user) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes

          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(this.currentUserValue);
    return of({ success: false });
  }
}*/
