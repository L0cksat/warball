
export interface League {
  id?: number;
  leagueName: string;
  leagueCountry: string;
  promoteTo: number | null;
  relegateTo: number | null;
}
