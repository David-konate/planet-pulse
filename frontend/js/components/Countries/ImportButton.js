// frontend/js/components/Countries/ImportButton.js
import AppComponent from '../lib/AppComponents.js';

class ImportButton extends AppComponent {

    template() {

        return /*html*/`
            <button class="btn btn-primary" type="button"> Import countries </button>
            <p class="import-message small mb-0 fade" role="status"></p>
        `;
    }

    afterRender() {
        // Get references to the button and message elements
        this.button = this.querySelector('button');
        this.message = this.querySelector('.import-message');

        //prevent importation if already imported
        if (this.props.imported) {
            this.markAsImported(`${this.props.importedCount} countries are already imported.`);
        }

        // 'this' is the component instance, so we can use it to access the component's methods and properties
        this.button.addEventListener('click', () => this.handleClick());
    }

    // called by the browser when the component is removed from the page
    disconnectedCallback() {
        clearTimeout(this.hideTimer);
    }

    async handleClick() {
        const { onImport } = this.props;
        if (!onImport) return;

        this.button.disabled = true; // avoid the double click
        this.button.textContent = 'The import is loading...';
        this.hideMessage();

        try {
            const result = await onImport();  // count, message
            this.markAsImported(`The import was successful: ${result.count} countries were imported.`, 4000);
        } catch (error) {
            console.error(error);
            if (error.status === 409) {
                this.markAsImported(error.message);
                return;
            }


            const text = error.status ? error.message : 'The server is unreachable';
            this.button.disabled = false;
            this.button.textContent = "Import countries";
            this.showMessage(text, 'text-danger');
        }
    }

    // it desact the button when the import is a success
    markAsImported(text, autoHideMs = 0){
        this.button.disabled = true;
        this.button.textContent = "Countries imported";
        this.showMessage(text, 'text-success', autoHideMs);
    }

    // autoHideMs > 0 : the message disappears after this delay (in milliseconds)
    // Bootstrap: "fade" = opacity 0 with a transition, "fade show" = opacity 1
    showMessage(text, cssClass, autoHideMs = 0) {
        clearTimeout(this.hideTimer); // cancel a previous timer so it doesn't hide the new message
        this.message.className = `import-message small mb-0 fade ${cssClass}`;
        this.message.textContent = text;

        // wait one frame so the browser paints opacity 0 first, otherwise the fade in is skipped
        requestAnimationFrame(() => this.message.classList.add('show'));

        if (autoHideMs > 0) {
            this.hideTimer = setTimeout(() => this.hideMessage(), autoHideMs);
        }
    }

    // fade out, then empty the text so the paragraph doesn't keep its height
    hideMessage() {
        clearTimeout(this.hideTimer);
        const text = this.message.textContent;
        this.message.classList.remove('show');
        this.message.addEventListener('transitionend', () => {
            // only clear if no new message was shown during the fade out
            if (!this.message.classList.contains('show') && this.message.textContent === text) {
                this.message.textContent = '';
            }
        }, { once: true });
    }
}

customElements.define('import-button', ImportButton);
