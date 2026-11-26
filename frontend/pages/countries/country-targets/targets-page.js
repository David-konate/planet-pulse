import AppComponent from "../../../js/components/lib/AppComponents.js";
import "../../../js/components/Targets/TargetsTable.js";
import { getTargetsByCountryId } from "../../../services/targets-services.js";
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
    return readCountryContext(this.dataset.param); // { countryId, countryName } ou null
  }

  get countryName() {
    return this.country?.countryName ?? this.dataset.param;
  }

  get countryId() {
    return this.country?.countryId;
  }
 

async beforeRender() {
  if (!isValidSlug(this.dataset.param)) {
    this.state = { error: ERRORS.INVALID };
    return;
  }
  if (this.countryId == null) {
    this.state = { error: ERRORS.MISSING_ID };
    return;
  }

  try {
    const targets = await getTargetsByCountryId(this.countryId);
    this.state = { targets };
  } catch (error) {
    console.error(error);
    this.state = { error: error.message };
  }
}
  template() {
    const { error, targets } = this.state;
    const countryName = this.countryName;
    const safeId = escapeHtml(String(this.countryId));
    console.log(this.countryName);

    return /*html*/ `
      <main class="container py-4">
        ${
          error
            ? `<div class="alert alert-danger" role="alert">${escapeHtml(error)}</div>
                 <add-target-button
          country-name="${escapeHtml(countryName)}"
          country-id="${safeId}">
        </add-target-button>`
            : `
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h1 id="targets-title" class="h3 mb-0">Targets for ${escapeHtml(countryName)}</h1>
        <add-target-button
          country-name="${escapeHtml(countryName)}"
          country-id="${safeId}">
        </add-target-button>
        </div>
          ${
            targets.length === 0
              ? `<p class="text-muted">No target yet for this country.</p>`
              : `
            <div class="card text-center">
          <div class="card-header d-flex justify-content-around align-items-center gap-2">
            <year-countainer class="d-flex justify-content-center">
                <label for="year-select" class="visually-hidden">Target year</label>
                <select id="year-select" class="form-select form-select-sm fw-bold text-center w-auto">
                </select>
            </year-countainer>
          <edit-target-button
            country-name="${escapeHtml(countryName)}"
            country-id="${safeId}"
            year-target="">
          </edit-target-button>
          </div>
              <div id="targets-table"></div>
            </div>`
          }`
        }
      </main>
    `;
  }

  afterRender() {
    const { error, targets } = this.state;
    if (error) return;

    const countryName = history.state?.countryName ?? this.dataset.param;
    document.title = `${countryName} - PlanetPulse`;

    if (targets.length === 0) return;

    this.targets = [...targets].sort((a, b) => a.targetYear - b.targetYear);

    const select = this.querySelector("#year-select");
   const options = this.targets.map((t) => {
     const year = String(t.targetYear);
     return new Option(year, year); // new Option(text, value)
   });

   select.replaceChildren(...options);

    select.addEventListener("change", () => this.showTarget(select.value));

    this.showTarget(select.value);
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
