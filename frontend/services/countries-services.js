//call data from countries.json file
export async function getCountries() {
    const url = new URL('../../data/countries-test.json', import.meta.url);
    const response = await fetch(url);
    if (!response.ok) throw new Error('Impossible de charger les pays');

    const json = await response.json();
    return json.data.objects.sort((a, b) => a.names.common.localeCompare(b.names.common));
}