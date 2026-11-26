import AppComponent from '../lib/AppComponents.js';
import { escapeHtml, safeUrl } from '../../../utils/sanitize.js';
import '../../../js/components/Button/TargetButton.js';
class CountryItem extends AppComponent {

    template() {
      const { iso, countryName, flagUrl, region } = this.props;
        const safeIso = escapeHtml(String(iso));
        const safeCountryName = escapeHtml(String(countryName));
            console.log("CountryItem: template", { iso, countryName, flagUrl, region });

        return /*html*/`
            <a href="/countries/${safeIso}" class="card h-100 text-center text-decoration-none p-3" data-iso="${safeIso}">
              <img src="${escapeHtml(safeUrl(flagUrl))}" alt="" class="country-flag" loading="lazy">
              <div class="card-body p-0 pt-3">
                <h2 class="card-title h6 mb-1">${escapeHtml(countryName)}</h2>
                <p class="card-text small mb-0">${escapeHtml(region)}</p>
              </div>
              <target-button
                  country-iso="${safeIso}"
                  country-name="${safeCountryName}"
                  class="position-relative"
                  style="z-index: 2">
              </target-button>
            </a>
        `;
    }
}

customElements.define('country-item', CountryItem);