
import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/indicator/IndicatorForm.js';

class AddIndicatorPage extends AppComponent {

    template() {
        return /*html*/`
            <main class="container py-4">
                <indicator-form></indicator-form>
            </main>
        `;
    }
}

customElements.define('add-indicator-page', AddIndicatorPage);
