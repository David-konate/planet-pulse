import AppComponent from "../../../js/components/lib/AppComponents.js";
import "../../../js/components/Targets/TargetsTable.js";
import { getTargetsBycountryIso } from "../../../services/targets-services.js";
import { escapeHtml } from "../../../utils/sanitize.js";
import { isValidSlug } from "../../../utils/slugify.js";
import "../../../js/components/Button/AddTargetButton.js";
import { readCountryContext } from "../../../utils/navigation.js";

const ERRORS = {
  INVALID: "Invalid country.",
};

class TargetsPage extends AppComponent {
  targets = [];

  get country() {
    return readCountryContext(this.dataset.param); // { countryIso, countryName } ou null
  }

  get countryName() {
    return this.country?.countryName ?? this.dataset.param;
  }

  get countryIso() {
    return this.country?.countryIso ?? this.dataset.param;
  }

  async beforeRender() {
    if (!isValidSlug(this.dataset.param)) {
      this.state = { error: ERRORS.INVALID };
      return;
    }
    if (this.countryIso == null) {
      this.state = { error: ERRORS.MISSING_ID };
      return;
    }
    try {
      const targets = await getTargetsBycountryIso(this.countryIso);
      this.state = { targets };
    } catch (error) {
      console.error(error);
      this.state = { error: error.message };
    }
  }
  template() {
    const { error, targets } = this.state;
    const countryName = this.countryName;
    const safeIso = escapeHtml(String(this.countryIso));

    return /*html*/ `
      <main class="container py-4">
      <infos id="infos-values">
      </infos>
        ${
          error
            ? `<div class="alert alert-info" role="alert">${escapeHtml(error)}</div>
                 <add-target-button
          country-name="${escapeHtml(countryName)}"
          country-iso="${safeIso}">
        </add-target-button>`
            : `
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h1 id="targets-title" class="h3 mb-0">Targets for ${escapeHtml(countryName)}</h1>
        <add-target-button
          country-name="${escapeHtml(countryName)}"
          country-iso="${safeIso}">
        </add-target-button>
        </div>
          ${
            targets.length === 0
              ? `<p class="text-muted">No target yet for this country.</p>`
              : `
            <div class="card text-center">
              <div class="accordion" id="accordion-targets"></div> 
            
            </div>`
          }`
        }
      </main>
    `;
  }

  afterRender() {
    const { error, targets } = this.state;
    if (error) return;
    const countryName = this.countryName;
    document.title = `${countryName} Targets - PlanetPulse`;

    if (targets.length === 0) return;

    this.targets = [...targets].sort((a, b) => a.targetYear - b.targetYear);

    const dataset = this.targets.map((target, index) => ({ target, open : index === 0 })); 
    const conteneur = this.querySelector("#accordion-targets");
    this.attachChildren(conteneur, "targets-table", dataset);

  }

  showTarget(year) {
    const target = this.targets.find((t) => String(t.targetYear) === year);
    if (!target) return;

    this.querySelector("edit-target-button")?.setAttribute("year-target", year);

    const container = this.querySelector("#targets-table");
    container.replaceChildren(); // évite d'empiler les anciens composants
    this.attachChild(container, "targets-table", { target });
  }
}

customElements.define("targets-page", TargetsPage);
