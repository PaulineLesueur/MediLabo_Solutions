import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({
    providedIn: 'root'
})

export class AuthPractitionerGuard implements CanActivate {
    constructor(private auth: AuthService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const token = this.auth.getToken();

        if(token) {
            const decodedToken = JSON.parse(atob(token.split('.')[1]));
            if(decodedToken && decodedToken.role && decodedToken.role === 'ROLE_PRACTITIONER') {
                return true;
            }
        }

        this.router.navigate(['/login']);
        return false;
    }
}