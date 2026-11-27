
import AppComponent from '../../js/components/lib/AppComponents.js';
import '../../js/components/Header/AppHeader.js';
import '../../js/components/indicator/IndicatorForm.js';

class AddIndicatorPage extends AppComponent {

    template() {
        return /*html*/`
            <main class="container py-4">

                <div class="d-flex justify-content-end mb-3">
                    <a href="../../index.html">&larr; Back to countries</a>
                </div>

                <indicator-form></indicator-form>

            </main>
        `;
    }
}

customElements.define('add-indicator-page', AddIndicatorPage);
