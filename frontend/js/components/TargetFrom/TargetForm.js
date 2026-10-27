import AppComponent from "../lib/AppComponents.js";

class TargetForm extends AppComponent {
  template() {
    return /*html*/ `
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
                     step="0.01" placeholder="Total fine particulate matter">
              <span class="input-group-text">µg/m³</span>
            </div>
          </div>

          <div class="mb-3">
            <label for="forest-area" class="form-label">Forest area</label>
            <div class="input-group">
              <input id="forest-area" name="forest_area" type="number" class="form-control"
                     step="0.01" placeholder="Share of total land area">
              <span class="input-group-text">%</span>
            </div>
          </div>

          <div class="mb-3">
            <label for="renewable-energy-share" class="form-label">Share of renewable energy</label>
            <div class="input-group">
              <input id="renewable-energy-share" name="renewable_energy_share" type="number" class="form-control"
                     step="0.01" placeholder="Share of total energy consumption">
              <span class="input-group-text">%</span>
            </div>
          </div>

          <div id="indicators-error" class="alert alert-danger d-none" role="alert">
            Provide information for at least one of the four indicators.
          </div>

          <button id="change-target-btn" type="submit" class="btn btn-primary">Save target</button>
        </form>
      </div>
    `;
  }

  afterRender() {
    this.form = this.querySelector("#target-form");
    this.feedback = this.querySelector("#form-feedback");
    this.errorDiv = this.querySelector("#indicators-error");
    this.submitBtn = this.querySelector("#change-target-btn");

    // Validation constraints for the input fields
    const yearInput = this.querySelector("#target-year");
    yearInput.min = new Date().getFullYear();
    setRangeMessages(yearInput, {
      underflow: `The target year must be ${yearInput.min} or later.`,
    });
    const co2Input = this.querySelector("#co2-emissions");
    co2Input.min = 0.01;
    setRangeMessages(co2Input, {
      underflow: "CO2 emissions must be at least 0.01 t.",
    });

    const pm25Input = this.querySelector("#pm25-emissions");
    pm25Input.min = 0.01;
    setRangeMessages(pm25Input, {
      underflow: "PM2.5 emissions must be at least 0.01 µg/m³.",
    });

    for (const id of ["#forest-area", "#renewable-energy-share"]) {
      const input = this.querySelector(id);
      input.min = 0;
      input.max = 100;
      setRangeMessages(input, {
        underflow: "The value cannot be negative.",
        overflow: "The value cannot exceed 100 %.",
      });
    }

    this.form.addEventListener("submit", (event) => {
      event.preventDefault();
      this.feedback.innerHTML = "";

      const formData = new FormData(this.form);
      const data = {
        targetYear: emptyToNull(formData.get("target_year")),
        co2Emissions: emptyToNull(formData.get("co2_emissions")),
        pm25Emissions: emptyToNull(formData.get("pm25_emissions")),
        forestArea: emptyToNull(formData.get("forest_area")),
        renewableEnergyShare: emptyToNull(
          formData.get("renewable_energy_share"),
        ),
      };

      const { co2Emissions, pm25Emissions, forestArea, renewableEnergyShare } =
        data;

      if (
        !co2Emissions &&
        !pm25Emissions &&
        !forestArea &&
        !renewableEnergyShare
      ) {
        this.errorDiv.classList.remove("d-none");
        return;
      }
      this.errorDiv.classList.add("d-none");

      this.submitBtn.disabled = true;
      this.dispatchEvent(
        new CustomEvent("target-submit", { detail: data, bubbles: true }),
      );
    });
  }

  // --- API publique, appelée par la page parente ---

  showSuccess() {
    this.feedback.innerHTML = /*html*/ `
      <div class="alert alert-success alert-dismissible" role="alert">
        Target saved successfully.
        <button type="button" class="btn-close" id="close-feedback" aria-label="Close"></button>
      </div>`;
    this.querySelector("#close-feedback").addEventListener("click", () => {
      this.feedback.innerHTML = "";
    });
    this.form.reset();
  }

  showError(message = "Unable to save the target. Please try again.") {
    this.feedback.innerHTML = /*html*/ `
    <div class="alert alert-danger" role="alert">${message}</div>`;
  }

  setBusy(isBusy) {
    this.submitBtn.disabled = isBusy;
  }
}

function emptyToNull(value) {
  if (value === null) {
    return null;
  }
  if (value === "") {
    return null;
  }
  return value;
}

function setRangeMessages(input, messages) {

  const underflowMessage = messages.underflow;
  const overflowMessage = messages.overflow;

  function onInputChange() {

    const validity = input.validity;
    const isTooLow = validity.rangeUnderflow; // valeur < min
    const isTooHigh = validity.rangeOverflow; // valeur > max

    let message;

    if (isTooLow) {
      message = underflowMessage;
    } else if (isTooHigh) {
      message = overflowMessage;
    } else {
      message = ""; // no erreur
    }


    input.setCustomValidity(message);
  }

  input.addEventListener("input", onInputChange);
}

customElements.define("target-form", TargetForm);
