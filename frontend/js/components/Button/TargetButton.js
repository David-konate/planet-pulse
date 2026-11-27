import { goToTargets } from "../../../utils/navigation.js";
import AppComponent from "../lib/AppComponents.js";

class TargetButton extends AppComponent {
  template() {
    return /*html*/ `
<button type="button"
        class="btn btn-light btn-sm py-0 px-1"
        id="target-button"
        aria-label="Access targets"
        title="Access targets">
    <svg id="svg-target" xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 16 16"
         fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" aria-hidden="true">
        <circle cx="8" cy="8" r="5"/>
        <line x1="8" y1="1" x2="8" y2="5"/>    <!-- nord -->
        <line x1="8" y1="11" x2="8" y2="15"/>  <!-- sud -->
        <line x1="1" y1="8" x2="5" y2="8"/>    <!-- ouest -->
        <line x1="11" y1="8" x2="15" y2="8"/>  <!-- est -->
    </svg>
</button>
        `;
  }

  afterRender() {
    this.querySelector("#target-button").addEventListener("click", (event) => {
      event.preventDefault();
      event.stopPropagation();

      const countryIso = this.getAttribute("country-iso");
      const countryName = this.getAttribute("country-name");
      if (!countryIso || !countryName) {
        console.error("target-button: missing country-iso or country-name");
        return;
      }

      goToTargets({ countryIso, countryName });
    });
  }
}

customElements.define("target-button", TargetButton);
