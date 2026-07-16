import { Routes } from '@angular/router';
import { TeamListComponent } from './components/team-list/team-list';
import { HomeComponent } from './components/home/home';
import { AdminTeamListComponent } from './components/admin/admin-team-list/admin-team-list';
import { AdminTeamFormComponent } from './components/admin/admin-team-form/admin-team-form';

export const routes: Routes = [
    { path: "", component: HomeComponent, pathMatch: 'full'},
    { path: "teams", component: TeamListComponent },
    { path: 'admin/teams', component: AdminTeamListComponent },
    { path: 'admin/teams/new', component: AdminTeamFormComponent },
    { path: 'admin/teams/:id/edit', component: AdminTeamFormComponent },
];
