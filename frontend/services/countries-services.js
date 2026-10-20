//call data from countries.json file
export async function getCountries() {
    const url = new URL('../../data/countries-clean.json', import.meta.url);
    const response = await fetch(url);
    if (!response.ok) throw new Error('Unable to load countries');

    const json = await response.json();
    return json.data.objects.sort((a, b) => a.names.common.localeCompare(b.names.common));
}

//call aleatory country from countries.json file
export async function getRandomCountry() {
    const countries = await getCountries();
    if (countries.length === 0) throw new Error("No country available");

    const index = Math.floor(Math.random() * countries.length);
    return countries[index];
}