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
    const iso = this.dataset.param ?? "";
    const titleElement = this.querySelector("#page-title");
    const feedback = this.querySelector("#page-feedback");
    const targetForm = this.querySelector("target-form");

    // the URL is the source of truth; history.state is only used for display
    if (!/^[A-Za-z]{2}$/.test(iso)) {
      feedback.replaceChildren(new ErrorMessage("Invalid country."));
      return;
    }

    const countryIso = iso.toUpperCase(); // the DB enforces ^[A-Z]{2}$
    console.log("Adding target for country:", countryIso);
    const countryName = history.state?.countryName ?? countryIso;
    this.country = { iso: countryIso, name: countryName };

    titleElement.textContent = `Add Target for ${countryName}`;

    targetForm.addEventListener("target-submit", async (event) => {
      // isEdit is added by TargetForm: drop it so it isn't sent to the API
      const { isEdit, ...data } = event.detail;

      try {
      const payload = { ...data, countryIso };
console.log("Payload envoyé:", payload);
await createTarget(payload);
      } catch (error) {
        console.error(error);
        feedback.replaceChildren(
          new ErrorMessage("Failed to save the target. Please try again."),
        );
        targetForm.setBusy(false);
        return;
      }

      feedback.replaceChildren(
        new SuccessMessage("Target saved successfully."),
      );
      targetForm.reset();
      targetForm.setBusy(false);
    });
  }
}

customElements.define("add-targets-page", AddTargetsPage);
