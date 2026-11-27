import AppComponent from '../lib/AppComponents.js';
import '../Countries/countryDetails.js'; // registers <country-details>
import { escapeHtml } from '../../../utils/sanitize.js';
import { getIndicatorTypes, getIndicatorsByCountry } from '../../../services/indicatorServices.js';

// The indicators of one country: country details, table, and a button to add indicators.
class DetailIndicator extends AppComponent {

    template() {
        const countryId = escapeHtml(String(this.props.countryIso)); // safe, but not used in the template
        return /*html*/`
            <div id="country"></div>

            <h2 class="h5 mb-2">Indicators</h2>
            <div id="table">Loading indicators...</div>

            <div class="mt-3">
                <a class="btn btn-primary" href="./add-indicator.html?country=${countryId}">
                    <i class="bi bi-plus-lg"></i> Add indicators
                </a>
            </div>
        `;
    }

    async afterRender() {
        this.attachChild(this.querySelector('#country'), 'country-details',
            { countryIso: this.props.countryIso });

        const box = this.querySelector('#table');
        try {
            // The columns come from the backend, like in the form
            const [types, rows] = await Promise.all([
                getIndicatorTypes(),
                getIndicatorsByCountry(this.props.countryIso)
            ]);

            box.innerHTML = rows.length === 0
                ? `<p class="text-muted">No indicator values yet.</p>`
                : this.#buildTable(types, rows);
        } catch (err) {
            box.textContent = err.message; // textContent: safe
        }
    }

    // One row per year. A missing value is shown as "-".
    #buildTable(types, rows) {
        const head = types
            .map((type) => `<th>${escapeHtml(type.label)} (${escapeHtml(type.unit)})</th>`)
            .join('');

        const body = rows.map((row) => /*html*/`
            <tr>
                <td>${escapeHtml(String(row.yearIndicator))}</td>
                ${types.map((type) => `<td>${escapeHtml(String(row[type.field] ?? '-'))}</td>`).join('')}
            </tr>
        `).join('');

        return /*html*/`
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead><tr><th>Year</th>${head}</tr></thead>
                    <tbody>${body}</tbody>
                </table>
            </div>
        `;
    }
}

customElements.define('detail-indicator', DetailIndicator);
