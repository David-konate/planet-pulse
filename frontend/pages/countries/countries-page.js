// frontend/pages/countries-page.js
import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/Header/AppHeader.js';
import '../../js/components/Countries/ImportButton.js';
import '../../js/components/Countries/CountriesList.js';
import { getCountries, getImportStatus, importCountries } from '../../services/countries-services.js';

class CountriesPage extends AppComponent {

    template() {
        return /*html*/`
            <main class="container py-4">
              <h1 class="h3 text-center mb-4">List Countries</h1>
              <div id="import-zone" class="mb-4"></div>
              <div id="countries-content">
                <p class="text-center">Chargement…</p>
              </div>
            </main>
        `;
    }

    async afterRender() {
        //default state, like this the btn stays clickable
        let status = { imported: false, count: 0};
        try {
            status = await getImportStatus();
        } catch (error) {
            console.error(error);
        }

        this.attachChild(this.querySelector('#import-zone'), 'import-button', {
            imported: status.imported,
            importedCount: status.count,
            onImport: importCountries,
        });
        
        const content = this.querySelector('#countries-content');
        try {
            const countries = await getCountries();
            content.innerHTML = '';
            this.attachChild(content, 'country-list', { countries });
        } catch (error) {
            console.error(error);
            content.innerHTML = `<div class="alert alert-danger" role="alert">
              Unable to load countries.</div>`;
        }
    }
}

customElements.define('countries-page', CountriesPage);