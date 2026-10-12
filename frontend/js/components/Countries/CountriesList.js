// frontend/js/components/Countries/countries-list.js
import AppComponent from '../lib/AppComponents.js';
import './CountriesItem.js';   // import "à effet de bord" : enregistre <country-item>

class CountryList extends AppComponent {

    template() {
        const { countries = [] } = this.props;

        if (countries.length === 0) {
            return /*html*/`<p class="text-center">Aucun pays à afficher.</p>`;
        }
        
        return /*html*/`<div class="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-3"></div>`;
    }

    afterRender() {
        const row = this.querySelector('.row');
        if (!row) return;   
        this.attachChildren(row, 'country-item', this.props.countries);
    }
}

customElements.define('country-list', CountryList);