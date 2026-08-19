export interface Team {
  id?: number;
  teamName: string;
  country: string;
  province: string | null;
  homePrimaryHex: string | null;
  homeSecondaryHex: string | null;
  awayPrimaryHex: string | null;
  awaySecondaryHex: string | null;
}
