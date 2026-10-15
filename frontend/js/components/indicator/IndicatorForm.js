import AppComponent from '../lib/AppComponents.js';

// The 4 indicators with their unit and maximum value (null = no maximum)
const FIELDS = [
    { name: 'co2Emissions',    label: 'CO₂ emissions',          unit: 'Mt',    max: null },
    { name: 'renewableEnergy', label: 'Renewable energy share', unit: '%',     max: 100 },
    { name: 'forestArea',      label: 'Forest area',            unit: '%',     max: 100 },
    { name: 'pm25Exposure',    label: 'PM2.5 exposure',         unit: 'µg/m³', max: null }
];

class IndicatorForm extends AppComponent {

    beforeRender() {
        // The country comes from the URL: add-indicator.html?country=FR
        const code = new URLSearchParams(window.location.search).get('country');
        // Only accept 2 capital letters, so nothing else can end up in the HTML
        this.state = { countryCode: /^[A-Z]{2}$/.test(code) ? code : null };
    }

    template() {
        return /*html*/`
            <h1 class="h3 mb-3">Add indicator values</h1>
            <p>Country: <strong>${this.state.countryCode ?? 'not selected'}</strong></p>

            <div id="message"></div>

            <form id="form" novalidate>
                <div class="mb-3">
                    <label for="year" class="form-label">Year *</label>
                    <input id="year" type="number" class="form-control">
                </div>

                ${FIELDS.map((field) => /*html*/`
                    <div class="mb-3">
                        <label for="${field.name}" class="form-label">${field.label} (${field.unit})</label>
                        <input id="${field.name}" type="number" step="0.01" class="form-control">
                    </div>
                `).join('')}

                <p class="text-muted">Fill in at least one indicator.</p>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-lg"></i> Save
                </button>
                <button type="button" id="cancel" class="btn btn-outline-secondary">Reset</button>
            </form>
        `;
    }

    afterRender() {
        this.querySelector('#form').addEventListener('submit', (event) => {
            event.preventDefault(); // stop the page from reloading
            this.#save();
        });
            this.querySelector('#cancel').addEventListener('click', () => {
            this.querySelector('#form').reset();   // empties the fields
            this.#clearMessage();                  // removes the error or success message
});
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
        } catch (error) {
            this.#showMessage(error.message, 'danger');
        }
    }

    // Reads the inputs. An empty input becomes null.
    #readForm() {
        const read = (id) => {
            const text = this.querySelector(`#${id}`).value.trim();
            return text === '' ? null : Number(text);
        };

        const data = { countryCode: this.state.countryCode, year: read('year') };
        FIELDS.forEach((field) => {
            data[field.name] = read(field.name);
        });
        return data;
    }

    // Returns an error message, or null if everything is valid
    #validate(data) {
        if (!data.countryCode) return 'The country is missing.';
        if (!Number.isInteger(data.year)) return 'The year is required and must be a whole number.';

        const filled = FIELDS.filter((field) => data[field.name] !== null);
        if (filled.length === 0) return 'Fill in at least one indicator.';

        for (const field of filled) {
            const value = data[field.name];
            if (value < 0) return `${field.label} must be 0 or more.`;
            if (field.max !== null && value > field.max) {
                return `${field.label} must be between 0 and ${field.max}.`;
            }
        }
        return null;
    }

    #showMessage(text, type) {
        const box = this.querySelector('#message');
        box.className = `alert alert-${type}`; // Bootstrap alert
        box.textContent = text;                // textContent is safe (no HTML injected)
    }

    #clearMessage() {
    const box = this.querySelector('#message');
    box.className = '';
    box.textContent = '';
}
}

customElements.define('indicator-form', IndicatorForm);
