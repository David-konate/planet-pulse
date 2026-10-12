import AppComponent from '../lib/AppComponents.js';

class CountryItem extends AppComponent {

    template() {
        const { names, region, flag } = this.props;

        const flagHtml = flag?.url_svg
            ? `<img src="${flag.url_svg}" alt="" class="country-flag" loading="lazy">`
            : `<div class="country-flag country-flag--empty" aria-hidden="true">🏳️</div>`;

        return /*html*/`
            <a href="#" class="card h-100 text-center text-decoration-none p-3">
              ${flagHtml}
              <div class="card-body p-0 pt-3">
                <h2 class="card-title h6 mb-1">${names.common}</h2>
                <p class="card-text small mb-0">${region}</p>
              </div>
            </a>
        `;
    }
}

customElements.define('country-item', CountryItem);