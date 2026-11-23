import AppComponent from "../../../js/components/lib/AppComponents.js";
import "../../../js/components/Header/AppHeader.js";
import "../../../js/components/Targets/TargetForm.js";
import { SuccessMessage } from "../../../js/components/messages/SuccessMessage.js";
import { ErrorMessage } from "../../../js/components/messages/ErrorMessage.js";
import { createTarget } from "../../../services/targets-services.js";

class AddTargetsPage extends AppComponent {
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

  afterRender() {
    const { countryId, countryName } = history.state ?? {};
    const titleElement = this.querySelector("#page-title");
    const feedback = this.querySelector("#page-feedback");
    const targetForm = this.querySelector("target-form");

    if (countryId && countryName) {
      this.country = { id: Number(countryId), name: countryName };
      titleElement.textContent = `Add Target for ${countryName}`;
    } else {
      titleElement.textContent = "Add Target";
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
          new SuccessMessage("Target saved successfully."),
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

customElements.define("add-targets-page", AddTargetsPage);
