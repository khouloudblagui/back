import { AuthService } from "../service/auth.service";
import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError, switchMap } from "rxjs/operators";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => {
        // Si une réponse 401 est reçue (non autorisée)
        if (err.status === 401) {
          const refreshToken = localStorage.getItem('refreshToken');

          // Si un refreshToken est disponible, tenter de le rafraîchir
          if (refreshToken) {
            return this.authService.refreshToken().pipe(
              switchMap((res: any) => {
                // Rafraîchir le token d'accès et relancer la requête originale
                localStorage.setItem('accessToken', res.accessToken);
                request = request.clone({
                  setHeaders: {
                    Authorization: `Bearer ${res.accessToken}`,
                  },
                });
                return next.handle(request);
              }),
              catchError(() => {
                // Si le refresh échoue, déconnexion et redirection vers login
                this.authService.logout();
                return throwError(() => new Error('Session expirée. Veuillez vous reconnecter.'));
              })
            );
          } else {
            // Si pas de refreshToken, déconnexion et redirection vers login
            this.authService.logout();
            return throwError(() => new Error('Session expirée. Veuillez vous reconnecter.'));
          }
        }

        // Si une autre erreur survient, la renvoyer
        const error = err.error.message || err.statusText;
        return throwError(() => new Error(error));
      })
    );
  }
}















/*export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if (err.status === 401) {
          // auto logout if 401 response returned from api
          this.authenticationService.logout();
          location.reload();
        }

        const error = err.error.message || err.statusText;
        return throwError(() => error);
      })
    );
  }
}






 /* intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if (err.status === 401) {
          // auto logout if 401 response returned from api
          this.authenticationService.logout();
          location.reload();
        }

        const error = err.error.message || err.statusText;
        return throwError(error);
      })
    );
  }
}*/
