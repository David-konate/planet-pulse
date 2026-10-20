// frontend/js/components/Countries/ImportButton.js
import AppComponent from '../lib/AppComponents.js';

// TODO: remove when the backend returns { count }: temporary value only
const FALLBACK_IMPORTED_COUNT = 245;

class ImportButton extends AppComponent {

    template() {

        return /*html*/`
            <button class="btn btn-primary" type="button"> Import countries </button>
            <p class="import-message small mb-0" role="status" hidden></p>
        `;
    }

    afterRender() {
        // Get references to the button and message elements
        this.button = this.querySelector('button');
        this.message = this.querySelector('.import-message');

        //prevent importation if already imported
        if (this.props.imported) {
            this.markAsImported(this.props.importedCount ?? FALLBACK_IMPORTED_COUNT);
        }

        // 'this' is the component instance, so we can use it to access the component's methods and properties
        this.button.addEventListener('click', () => this.handleClick());
    }

    async handleClick() {
        const { onImport } = this.props;
        if (!onImport) return;

        this.button.disabled = true; // avoid the double click
        this.button.textContent = 'The import is loading...';
        this.message.hidden = true;

        try {
            const result = await onImport();  // undefined today, { count: 245 } tomorrow
            this.markAsImported(result?.count ?? FALLBACK_IMPORTED_COUNT); //if success = state imported
        } catch (error) {
            console.error(error);
            this.button.disabled = false;
            this.button.textContent = "Import countries";
            this.showMessage("The countries could not be imported.", 'text-danger');
        }
    }

    // it desact the button when the import is a success
    markAsImported(count){
        this.button.disabled = true;
        this.button.textContent = "Import countries";
        this.showMessage(`The import was successful: ${count} countries were imported`, 'text-success');
    }

    showMessage(text, cssClass) {
        this.message.textContent = text;
        this.message.className = `import-message small mb-0 ${cssClass}`;
        this.message.hidden = false;
    }
}

customElements.define('import-button', ImportButton);
