import AppComponent from "../../js/components/lib/AppComponents.js";
import "../../js/components/Header/AppHeader.js";
import "../../js/components/TargetFrom/targetForm.js";
import { getRandomCountry } from "../../services/countries-services.js";
import { createTarget } from "../../services/target-service.js";

class TargetsPage extends AppComponent {
  constructor() {
    super();
    this.country = null;
  }

  template() {
    return /*html*/ `
      <main class="container py-4">
        <h1 class="h3 text-center mb-4" id="page-title"></h1>
        <target-form></target-form>
      </main>
    `;
  }

  async afterRender() {
    const titleElement = this.querySelector("#page-title");
    const targetForm = this.querySelector("target-form");

    try {
      this.country = await getRandomCountry();
      titleElement.textContent = `Target Countries for ${this.country.names.common}`;
    } catch (error) {
      console.error(error);
    }

    targetForm.addEventListener("target-submit", async (event) => {
      const targetData = {
        countryId: 1, // TODO: use the id of the country loaded above (this.country)
        ...event.detail,
      };

      try {
        await createTarget(targetData);
        targetForm.showSuccess();
      } catch (error) {
        console.error(error);
        targetForm.showError();
      } finally {
        targetForm.setBusy(false);
      }
    });
  }
}

customElements.define("targets-page", TargetsPage);
