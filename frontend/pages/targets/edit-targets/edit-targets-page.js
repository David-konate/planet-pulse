import AppComponents from "../../../js/components/lib/AppComponents.js";
import "../../../js/components/Header/AppHeader.js";
import "../../../js/components/Targets/TargetForm.js";
import { SuccessMessage } from "../../../js/components/messages/SuccessMessage.js";
import { ErrorMessage } from "../../../js/components/messages/ErrorMessage.js";                 
import { getTargetsByCountryId } from "../../../services/targets-services.js";

class EditTargetsPage extends AppComponents {
  constructor() {
    super();
    this.country = null;
  }

    async beforeRender() {
    console.log("history.statea", history.state);
    const { countryId, countryName, targetYear } = history.state ?? {};
    this.country = { id: countryId, name: countryName, year: targetYear };
    try {
      const targets = await getTargetsByCountryId(countryId);
      this.state = { targets };
    } catch (error) {
      console.error("Error fetching targets:", error);
      this.state = { targets: [], error: error.message };
    }
  }

  template() {
    const year = history.state?.targetYear;

    return /*html*/ `
   <main class="container py-4">
      <h1 class="h3 text-center mb-4" id="page-title"></h1>
      <div id="page-feedback"></div>
      <target-form ${year ? `target-year="${year}"` : ""}></target-form>
    </main>
    `;
  }

  afterRender() {
    const form = this.querySelector("target-form");
    const { targets = [] } = this.state;

    const target = targets.find(
      (t) => String(t.targetYear) === String(this.country.year),
    );
    if (target) form.fill(target);
  }
}
customElements.define("edit-targets-page", EditTargetsPage);