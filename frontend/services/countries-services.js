//call data from countries.json file
export async function getCountries() {
    const url = new URL('../../data/countries-test.json', import.meta.url);
    const response = await fetch(url);
    if (!response.ok) throw new Error('Impossible de charger les pays');

    const json = await response.json();
    return json.data.objects.sort((a, b) => a.names.common.localeCompare(b.names.common));
}

// Backend base URL (Spring Boot default port)
const API_BASE_URL = 'http://localhost:8080/api';

// Ask the backend to import countries from the external API
export async function importCountries() {
    const response = await fetch(`${API_BASE_URL}/countries/import`, {
        method: 'POST',
    });

    if (!response.ok) throw new Error(`Import failed (${response.status})`);

    return response.json();
}
