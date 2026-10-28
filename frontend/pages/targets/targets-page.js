import AppComponent from "../../js/components/lib/AppComponents.js";
import "../../js/components/Header/AppHeader.js";
import "../../js/components/TargetForm/TargetForm.js";
import { SuccesMessage } from "../../js/components/messages/SuccesMessage.js";
import { ErrorMessage } from "../../js/components/messages/ErrorMessage.js";
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
        <div id="page-feedback"></div>
        <target-form></target-form>
      </main>
    `;
  }

  async afterRender() {
    const titleElement = this.querySelector("#page-title");
    const feedback = this.querySelector("#page-feedback");
    const targetForm = this.querySelector("target-form");

    try {
      this.country = await getRandomCountry();
      titleElement.textContent = `Target Countries for ${this.country.names.common}`;
    } catch (error) {
      console.error(error);
      feedback.replaceChildren(
        new ErrorMessage("Unable to load the country. Please reload the page."),
      );
    }

    targetForm.addEventListener("target-submit", async (event) => {
      if (!this.country) {
        feedback.replaceChildren(
          new ErrorMessage("No country loaded. Please reload the page."),
        );
        targetForm.setBusy(false);
        return;
      }

      const targetData = {
        countryId: this.country.id,
        ...event.detail,
      };

      try {
        await createTarget(targetData);
        feedback.replaceChildren(
          new SuccesMessage("Target saved successfully."),
        );
        targetForm.reset();
      } catch (error) {
        console.error(error);
        feedback.replaceChildren(
          new ErrorMessage("Failed to save the target. Please try again."),
        );
      } finally {
        targetForm.setBusy(false);
      }
    });
  }
}

customElements.define("targets-page", TargetsPage);
