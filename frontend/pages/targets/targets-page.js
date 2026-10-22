// frontend/pages/targets-page.js
import AppComponent from "../../js/components/lib/AppComponents.js";
import "../../js/components/Header/AppHeader.js";
import { getRandomCountry } from "../../services/countries-services.js";
import { createTarget } from "../../services/target-service.js";

// Converts an empty form value ("") to null, so the API receives null instead of ""
const emptyToNull = (value) => (value === null || value === "" ? null : value);

class TargetsPage extends AppComponent {
  constructor() {
    super();
    this.country = null;
  }

  template() {
    return /*html*/ `
    <main class="container py-4">
      <h1 class="h3 text-center mb-4" id="page-title"></h1>

      <div class="form-field" id="form-content">
        <div id="form-feedback"></div>

        <form id="target-form">

          <div class="mb-3">
            <label for="target-year" class="form-label">Target year</label>
            <input id="target-year" name="target_year" type="number" class="form-control"
                   step="1" placeholder="Enter target year (YYYY)" required>
          </div>

          <div class="mb-3">
            <label for="co2-emissions" class="form-label">CO2 emissions</label>
            <div class="input-group">
              <input id="co2-emissions" name="co2_emissions" type="number" class="form-control"
                     step="0.01" placeholder="Total CO2 emissions">
              <span class="input-group-text">t</span>
            </div>
          </div>

          <div class="mb-3">
            <label for="pm25-emissions" class="form-label">PM2.5 emissions</label>
            <div class="input-group">
              <input id="pm25-emissions" name="pm25_emissions" type="number" class="form-control"
                     min="0" step="0.01" placeholder="Total fine particulate matter">
              <span class="input-group-text">µg/m³</span>
            </div>
          </div>

          <div class="mb-3">
            <label for="forest-area" class="form-label">Forest area</label>
            <div class="input-group">
              <input id="forest-area" name="forest_area" type="number" class="form-control"
                     min="0" max="100" step="0.01" placeholder="Share of total land area">
              <span class="input-group-text">%</span>
            </div>
          </div>

          <div class="mb-3">
            <label for="renewable-energy-share" class="form-label">Share of renewable energy</label>
            <div class="input-group">
              <input id="renewable-energy-share" name="renewable_energy_share" type="number" class="form-control"
                     min="0" max="100" step="0.01" placeholder="Share of total energy consumption">
              <span class="input-group-text">%</span>
            </div>
          </div>

          <div id="indicators-error" class="alert alert-danger d-none" role="alert">
            Provide information for at least one of the four indicators.
          </div>

          <button id="change-target-btn" type="submit" class="btn btn-primary">Save target</button>
        </form>
      </div>
    </main>
  `;
  }

  async afterRender() {
    const titleElement = this.querySelector("#page-title");
    const yearInput = this.querySelector("#target-year");
    const co2Input = this.querySelector("#co2-emissions");
    const pm25Input = this.querySelector("#pm25-emissions");
    const forestInput = this.querySelector("#forest-area");
    const renewableInput = this.querySelector("#renewable-energy-share");
    const form = this.querySelector("#target-form");
    const feedback = this.querySelector("#form-feedback");
    const errorDiv = this.querySelector("#indicators-error");
    const submitBtn = this.querySelector("#change-target-btn");
    const currentYear = new Date().getFullYear();

    // Validation constraints for the input fields
    yearInput.min = currentYear;
    co2Input.min = 0.01;
    pm25Input.min = 0.01;
    forestInput.min = 0;
    forestInput.max = 100;
    renewableInput.min = 0;
    renewableInput.max = 100;

    try {
      this.country = await getRandomCountry();
      console.log("Random country:", this.country);
      titleElement.textContent = `Target Countries for ${this.country.names.common}`;
    } catch (error) {
      console.error(error);
    }

    form.addEventListener("submit", async (event) => {
      event.preventDefault();
      feedback.innerHTML = "";

      const formData = new FormData(form);
      const targetData = {
        countryId: 1, // TODO: use the id of the country loaded above (this.country)
        targetYear: emptyToNull(formData.get("target_year")),
        co2Emissions: emptyToNull(formData.get("co2_emissions")),
        pm25Emissions: emptyToNull(formData.get("pm25_emissions")),
        forestArea: emptyToNull(formData.get("forest_area")),
        renewableEnergyShare: emptyToNull(
          formData.get("renewable_energy_share"),
        ),
      };

      if (
        targetData.co2Emissions === null &&
        targetData.pm25Emissions === null &&
        targetData.forestArea === null &&
        targetData.renewableEnergyShare === null
      ) {
        errorDiv.classList.remove("d-none");
        return;
      }
      errorDiv.classList.add("d-none");

      submitBtn.disabled = true;

      try {
        const created = await createTarget(targetData);
        console.log("Target saved:", created);
        feedback.innerHTML = /*html*/ `
          <div class="alert alert-success alert-dismissible" role="alert">
            Target saved successfully.
            <button type="button" class="btn-close" id="close-feedback" aria-label="Close"></button>
          </div>`;
        this.querySelector("#close-feedback").addEventListener("click", () => {
          feedback.innerHTML = "";
        });
        form.reset();
      } catch (error) {
        console.error(error);
        feedback.innerHTML = /*html*/ `
          <div class="alert alert-danger" role="alert">
            Unable to save the target. Please try again.
          </div>`;
      } 
    });
  }
}

customElements.define("targets-page", TargetsPage);
