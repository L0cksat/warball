export const COUNTRY_FLAGS: Record<string, string> = {
  'Galonian Empire': '/flags/galonian-empire.png',
  'Drakonar Empire': '/flags/drakonar-empire.svg',
  'Council of Zerathos': '/flags/council-of-zerathos.svg',
};

export function countryFlagSrc(country: string | null | undefined): string | null {
  if (!country) {
    return null;
  }
  return COUNTRY_FLAGS[country] ?? null;
}
