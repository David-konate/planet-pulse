import {API_BASE_URL} from "../config.js";

//call data from countries.json file
export async function getCountries() {
    const url = new URL('../../data/countries-clean.json', import.meta.url);
    const response = await fetch(url);
    if (!response.ok) throw new Error('Unable to load countries');

    const json = await response.json();
    return json.countries.sort((a, b) => a.names.common.localeCompare(b.names.common));
}

//Ask the back if the countries are already in database
export async function getImportStatus() {
    const response = await fetch(`${API_BASE_URL}/countries/import/status`);
    if(!response.ok) throw new Error(`Status check failed (${response.status})`);

    return response.json(); // it show the imported and count
}

// Ask the backend to import countries from the external API
export async function importCountries() {
    const response = await fetch(`${API_BASE_URL}/countries/import`, {
        method: 'POST',
    });

    if (!response.ok) {
        let message = `Import failed (${response.status})`;
        try {
            const body = await response.json();
            if (body?.message) message = body.message;
        } catch {
            //
        }

        const error = new Error(message);
        error.status = response.status;
        throw error;
    }

    return response.json(); // count, message
}

//call aleatory country from countries.json file
export async function getRandomCountry() {
    const countries = await getCountries();
    if (countries.length === 0) throw new Error("No country available");

    const index = Math.floor(Math.random() * countries.length);
    return countries[index];
}
