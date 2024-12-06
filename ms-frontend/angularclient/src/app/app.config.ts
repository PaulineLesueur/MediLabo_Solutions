import { provideHttpClient, withInterceptors } from "@angular/common/http";
import { ApplicationConfig } from "@angular/core";
import { authInterceptor } from "./services/auth.interceptor";

export const appConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(
            withInterceptors([authInterceptor])
        )
    ]
}