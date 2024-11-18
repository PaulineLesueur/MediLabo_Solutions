import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({
    providedIn: 'root'
})

export class AuthOrganizerGuard implements CanActivate {
    constructor(private auth: AuthService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const token = this.auth.getToken();
    
        // Si le token n'existe pas, rediriger vers la page de login
        if (!token) {
            this.router.navigate(['/login']);
            return false;
        }
    
        // Si le token existe, on décode le token
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        console.log("Token décodé dans le guard:", decodedToken);
    
        // Vérifier si le token a un rôle approprié
        if (decodedToken && decodedToken.role && decodedToken.role === 'ROLE_ORGANIZER') {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }
}