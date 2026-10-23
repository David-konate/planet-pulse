// frontend/pages/countries-page.js
import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/Header/AppHeader.js';
import '../../js/components/Countries/ImportButton.js';
import '../../js/components/Countries/CountriesList.js';
import { getCountries, importCountries } from '../../services/countries-services.js';
import { MESSAGES } from './../../utils/dictionary-objets.js';   

class CountriesPage extends AppComponent {

    // Step 1: load the data
    async beforeRender() {
        try {
            this.state = { countries: await getCountries() };
        } catch (error) {
            console.error(error);
            this.state = { error: MESSAGES.LOAD_ERROR };
        }
    }

    // Step 2: display based on the state
    template() {
        const { error } = this.state;

        return /*html*/`
            <main class="container py-4">
              <h1 class="h3 text-center mb-4">${MESSAGES.PAGE_TITLE}</h1>
              <div id="import-zone" class="mb-4"></div>
              ${error
                ? `<div class="alert alert-danger" role="alert">${error}</div>`
                : `<div id="countries-content"></div>`}
            </main>
        `;
    }

    // Step 3: only attach the children
    afterRender() {
        this.attachChild(this.querySelector('#import-zone'), 'import-button', {
            imported: false, 
            onImport: importCountries,
        });

        const content = this.querySelector('#countries-content');
        if (content) {
            this.attachChild(content, 'country-list', { countries: this.state.countries });
        }
    }
}

customElements.define('countries-page', CountriesPage);