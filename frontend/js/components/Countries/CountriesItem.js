import AppComponent from '../lib/AppComponents.js';
import { escapeHtml, safeUrl } from '../../../utils/sanitize.js';

class CountryItem extends AppComponent {

    template() {
        const { id, countryName, flagUrl, region } = this.props;

        return /*html*/`
            <a href="#/countries/${escapeHtml(String(id))}" class="card h-100 text-center text-decoration-none p-3" data-id="${escapeHtml(String(id))}">
              <img src="${escapeHtml(safeUrl(flagUrl))}" alt="" class="country-flag" loading="lazy">
              <div class="card-body p-0 pt-3">
                <h2 class="card-title h6 mb-1">${escapeHtml(countryName)}</h2>
                <p class="card-text small mb-0">${escapeHtml(region)}</p>
              </div>
            </a>
        `;
    }
}

customElements.define('country-item', CountryItem);