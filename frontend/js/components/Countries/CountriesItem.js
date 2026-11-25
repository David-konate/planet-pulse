import AppComponent from '../lib/AppComponents.js';
import { escapeHtml, safeUrl } from '../../../utils/sanitize.js';
import '../Button/TargetButton.js';

class CountryItem extends AppComponent {
  template() {
    const { id, countryName, flagUrl, region } = this.props;
    const safeId = escapeHtml(String(id));

<<<<<<< HEAD
    return /*html*/ `
        <div class="card h-100 text-center p-3 position-relative" data-id="${safeId}">
          <img src="${escapeHtml(safeUrl(flagUrl))}" alt="" class="country-flag" loading="lazy">
          <div class="card-body p-0 pt-3">
            <h2 class="card-title h6 mb-1">
              <a href="#/countries/${safeId}" class="stretched-link text-decoration-none">
                ${escapeHtml(countryName)}
              </a>
            </h2>
            <p class="card-text small mb-0">${escapeHtml(region)}</p>
          </div>
          <target-button
              country-id="${safeId}"
              country-name="${escapeHtml(String(countryName))}"
              class="position-relative"
              style="z-index: 2">
          </target-button>
        </div>
    `;
  }
=======
    template() {
        const { id, countryName, flagUrl, region } = this.props;

        return /*html*/`
            <a href="../indicator/country-indicators.html?country=${escapeHtml(String(id))}" class="card h-100 text-center text-decoration-none p-3" data-id="${escapeHtml(String(id))}">
              <img src="${escapeHtml(safeUrl(flagUrl))}" alt="" class="country-flag" loading="lazy">
              <div class="card-body p-0 pt-3">
                <h2 class="card-title h6 mb-1">${escapeHtml(countryName)}</h2>
                <p class="card-text small mb-0">${escapeHtml(region)}</p>
              </div>
            </a>
        `;
    }
>>>>>>> origin/develop
}

customElements.define('country-item', CountryItem);