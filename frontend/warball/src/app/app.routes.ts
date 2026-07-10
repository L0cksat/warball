import { Routes } from '@angular/router';
import { TeamListComponent } from './components/team-list/team-list';
import { HomeComponent } from './components/home/home';

export const routes: Routes = [
    { path: "", component: HomeComponent, pathMatch: 'full'},
    { path: "teams", component: TeamListComponent }
];
