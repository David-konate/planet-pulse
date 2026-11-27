import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/indicator/detailIndicator.js'; // registers <detail-indicator>


// Page opened by the "Indicators" button of a country card.
class DetailIndicatorPage extends AppComponent {

    beforeRender() {
        // The country code comes from the URL: detail-indicator.html?country=5
        const code = new URLSearchParams(window.location.search).get('country');
        // Only accept a two-letter country code
        this.state = { countryIso: /^[A-Z]{2}$/.test(code) ? code : null };
    }

    template() {
        if (this.state.countryIso === null) {
            return /*html*/`<main class="container py-4"><p>Country not selected.</p></main>`;
        }

        return /*html*/`
            <main class="container py-4">
                <div class="d-flex justify-content-end mb-3">
                    <a href="../../index.html">&larr; Back to countries</a>
                </div>
                <div id="detail"></div>
            </main>
        `;
    }

    afterRender() {
        if (this.state.countryIso === null) return;

        this.attachChild(this.querySelector('#detail'), 'detail-indicator',
            { countryIso: this.state.countryIso });
    }
}

customElements.define('detail-indicator-page', DetailIndicatorPage);
