// frontend/pages/targets-page.js
import AppComponent from "../../js/components/lib/AppComponents.js";
import "../../js/components/Header/AppHeader.js";
import { getRandomCountry } from "../../services/countries-services.js";

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
        <form id="target-form" >

          <div class="mb-3">
            <label for="target-year" class="form-label">Target year</label>
            <input id="target-year" name="year_" type="number" class="form-control"
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
            Renseigne au moins un des quatre indicateurs.
          </div>

          <button id="change-target-btn" type="submit" class="btn btn-primary">Save target</button>
        </form>
      </div>
    </main>
  `;
  }

  async afterRender() {
    const titleElement = this.querySelector("#page-title");
    const content = this.querySelector("#form-content");
    const yearInput = this.querySelector("#target-year");
    const co2Input = this.querySelector("#co2-emissions");
    const pm25Input = this.querySelector("#pm25-emissions");
    const forestInput = this.querySelector("#forest-area");
    const renewableInput = this.querySelector("#renewable-energy-share");
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
      content.innerHTML = /*html*/ `
        <div class="alert alert-danger" role="alert">
          Impossible de charger le pays.
        </div>`;
    }

    const form = this.querySelector("#target-form");

    form.addEventListener("submit", (event) => {
      event.preventDefault();

      const formData = new FormData(form);
      const targetData = {
        iso3166: this.country.codes.alpha_2,
        year: formData.get("year_"),
        co2_emissions: formData.get("co2_emissions"),
        pm25_emissions: formData.get("pm25_emissions"),
        forest_area: formData.get("forest_area"),
        renewable_energy_share: formData.get("renewable_energy_share")
      };
      if (!targetData.co2_emissions && !targetData.pm25_emissions && !targetData.forest_area && !targetData.renewable_energy_share) {
        const errorDiv = this.querySelector("#indicators-error");
        errorDiv.classList.remove("d-none");
        return;
      } else {
        console.log("form soumis:", targetData);
      }
    
    });
  }
}

customElements.define("targets-page", TargetsPage);
