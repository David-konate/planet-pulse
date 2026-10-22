import AppComponent from '../lib/AppComponents.js';
import { addIndicator, getIndicatorTypes } from '../../../services/indicatorServices.js';

class IndicatorForm extends AppComponent {

    beforeRender() {
        
    const params = new URLSearchParams(window.location.search);
    const id = params.get('countryId');

    console.log('Current URL:', window.location.href);
    console.log('Search params:', window.location.search);
    console.log('Country ID:', id);

    this.state = {
        countryId: /^\d+$/.test(id ?? '') ? Number(id) : null,
        indicators: []
    };
    }

    template() {
        return /*html*/`
            <h1 class="h3 mb-3">Add indicator values</h1>
            <p>Country: <strong>${this.state.countryId ?? 'not selected'}</strong></p>

            <div id="message"></div>

            <div class="card">
                <div class="card-body">
                    <form id="form" novalidate>
                        <!-- The indicator fields are added here when they are loaded -->
                        <div id="fields" class="row g-3">
                            <div class="col-12 col-md-6">
                                <label for="year" class="form-label">Year *</label>
                                <input id="year" type="number" class="form-control">
                            </div>
                        </div>

                        <div class="d-flex gap-2 mt-3">
                            <button type="submit" id="save" class="btn btn-primary" disabled>
                                <i class="bi bi-check-lg"></i> Save
                            </button>
                            <button type="button" id="cancel" class="btn btn-secondary">Reset</button>
                        </div>
                    </form>
                </div>
            </div>
        `;
    }

    async afterRender() {
        this.querySelector('#form').addEventListener('submit', (event) => {
            event.preventDefault(); // stop the page from reloading
            this.#save();
        });
        this.querySelector('#cancel').addEventListener('click', () => {
            this.querySelector('#form').reset(); // empties the fields
            this.#clearMessage();                // removes the error or success message
        });

        // Load the indicators, then build the fields
        try {
            this.state.indicators = await getIndicatorTypes();
            this.#showFields();
        } catch (err) {
            this.#showMessage(err.message, 'danger');
        }
    }

    // Adds one input per indicator. The texts come from our own backend, not from the user.
    #showFields() {
        const html = this.state.indicators.map((indicator) => /*html*/`
            <div class="col-12 col-md-6">
                <label for="${indicator.field}" class="form-label">${indicator.label} (${indicator.unit})</label>
                <input id="${indicator.field}" type="number" step="0.01" class="form-control">
            </div>
        `).join('');

        this.querySelector('#fields').insertAdjacentHTML('beforeend', html);
        this.querySelector('#save').disabled = false; // the form can be used now
    }

    async #save() {
        const data = this.#readForm();
        const error = this.#validate(data);
        if (error) {
            this.#showMessage(error, 'danger');
            return;
        }

        try {
            await addIndicator(data);
            this.#showMessage('Indicator values were added.', 'success');
            this.querySelector('#form').reset();
        } catch (err) {
            this.#showMessage(err.message, 'danger');
        }
    }

    // Reads the inputs. An empty input becomes null.
    #readForm() {
        const read = (id) => {
            const text = this.querySelector(`#${id}`).value.trim();
            return text === '' ? null : Number(text);
        };

        const data = { countryId: this.state.countryId, yearIndicator: read('year') };
        this.state.indicators.forEach((indicator) => {
            data[indicator.field] = read(indicator.field);
        });
        return data;
    }

    // Returns an error message, or null if everything is valid
    #validate(data) {
        if (!data.countryId) return 'The country is missing.';
        if (!Number.isInteger(data.yearIndicator)) return 'The year is required and must be a whole number.';

        const filled = this.state.indicators.filter((indicator) => data[indicator.field] !== null);
        if (filled.length === 0) return 'Fill in at least one indicator.';

        for (const indicator of filled) {
            const value = data[indicator.field];
            if (value < 0) return `${indicator.label} must be 0 or more.`;
            if (indicator.max !== null && value > indicator.max) {
                return `${indicator.label} must be between 0 and ${indicator.max}.`;
            }
        }
        return null;
    }

    #clearMessage() {
        const box = this.querySelector('#message');
        box.className = '';
        box.textContent = '';
    }

    #showMessage(text, type) {
        const box = this.querySelector('#message');
        box.className = `alert alert-${type}`; // Bootstrap alert
        box.textContent = text;                // textContent is safe (no HTML injected)
    }

    
}

    




customElements.define('indicator-form', IndicatorForm);
