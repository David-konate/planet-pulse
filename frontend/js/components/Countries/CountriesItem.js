import AppComponent from '../lib/AppComponents.js';
import { escapeHtml, safeUrl } from '../../../utils/sanitize.js';
import '../../../js/components/Button/TargetButton.js';
class CountryItem extends AppComponent {

    template() {
        const { iso, countryName, flagUrl, region } = this.props;
      const countryIso = escapeHtml(iso);
      const safeCountryName = escapeHtml(countryName);

        return /*html*/`
            <div class="card h-100 text-center p-3" data-iso="${countryIso}">
              <img src="${escapeHtml(safeUrl(flagUrl))}" alt="" class="country-flag" loading="lazy">
              <div class="card-body p-0 pt-3">
                <h2 class="card-title h6 mb-1">${escapeHtml(countryName)}</h2>
                <p class="card-text small mb-0">${escapeHtml(region)}</p>
              </div>
              <div class="d-flex justify-content-center gap-2 mt-3">
                <a class="btn btn-outline-primary btn-sm" href="/frontend/pages/indicator/detail-indicator.html?iso=${countryIso}">Indicators</a>
                       <target-button
                  country-iso="${countryIso}"
                  country-name="${safeCountryName}"
                  class="position-relative"
                  style="z-index: 2">
              </target-button>
            </a>
              </div>
            </div>
        `;
    }
}

customElements.define('country-item', CountryItem);