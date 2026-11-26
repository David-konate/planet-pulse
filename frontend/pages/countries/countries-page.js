// frontend/pages/countries-page.js
import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/Header/AppHeader.js';
import '../../js/components/Countries/ImportButton.js';
import '../../js/components/Countries/CountriesList.js';
import { getCountries, getImportStatus, importCountries } from '../../services/countries-services.js';
import { MESSAGES } from './../../utils/dictionary-objets.js';   

class CountriesPage extends AppComponent {

    // Step 1: load the data
    async beforeRender() {
        //default state, like this the btn stays clickable
        let status = { imported: false, count: 0};
        try {
            status = await getImportStatus();
        } catch (error) {
            console.error(error);
        }

        try {
            this.state = { importStatus: status, countries: await getCountries() };
        } catch (error) {
            console.error(error);
            this.state = { importStatus: status, error: MESSAGES.LOAD_ERROR };
        }
    }

    // Step 2: display based on the state
    template() {
        const { error } = this.state;

        return /*html*/ `
           <main class="container pt-5 pb-4 pt-md-4">
              <h1 class="h1 text-center mb-4">${MESSAGES.PAGE_TITLE}</h1>
              <div id="import-zone" class="mb-4"></div>
              ${
                error
                  ? `<div class="alert alert-danger" role="alert">${error}</div>`
                  : `<div id="countries-content"></div>`
              }
            </main>
        `;
    }

    // Step 3: only attach the children
    afterRender() {
        const { importStatus } = this.state;
        this.attachChild(this.querySelector('#import-zone'), 'import-button', {
            imported: importStatus.imported,
            importedCount: importStatus.count,
            onImport: () => this.handleImport(),
        });

        const content = this.querySelector('#countries-content');
        if (content) {
            this.attachChild(content, 'country-list', { countries: this.state.countries });
        }
    }
    //Import the countries and update the state
    async handleImport() {
        const result = await importCountries();
        console.log("Import result:", result);
        await this.refreshCountries();
        return result;
    }
    //Reload the countries from the backend and display them again
    async refreshCountries() {
        const content = this.querySelector('#countries-content');
        if (!content) return;

        try {
            const countries = await getCountries();
            content.replaceChildren();
            this.attachChild(content, 'country-list', { countries });
        } catch (error) {
            console.error(error);
        }
    }
}

customElements.define('countries-page', CountriesPage);