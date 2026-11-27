import AppComponent from '../lib/AppComponents.js';
import { escapeHtml, safeUrl } from '../../../utils/sanitize.js';
import { getCountries } from '../../../services/countries-services.js';

// SHARED block: common name, flag and region of a country.
// It is used on every page about a country (indicators, targets, notes),
class CountryDetails extends AppComponent {

    template() {
        return /*html*/`<div id="country-details" class="d-flex align-items-center gap-3 mb-3">Loading country...</div>`;
    }

    async afterRender() {
        const box = this.querySelector('#country-details');
        try {
            // TODO: use a "get one country" route when the countries backend has it
            const countries = await getCountries();
            const country = countries.find((item) => item.iso === this.props.countryIso);

            if (!country) {
                box.textContent = 'The country could not be found.';
                return;
            }

            box.innerHTML = /*html*/`
                <img src="${escapeHtml(safeUrl(country.flagUrl))}" alt="" height="40" class="border">
                <div>
                    <h1 class="h4 mb-0">${escapeHtml(country.countryName)}</h1>
                    <p class="text-muted mb-0">${escapeHtml(country.region)}</p>
                </div>
            `;
        } catch (err) {
            box.textContent = err.message; // textContent: safe
        }
    }
}

customElements.define('country-details', CountryDetails);
