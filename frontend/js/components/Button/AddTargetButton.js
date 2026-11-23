// js/components/Targets/AddTargetButton.js
import { slugify } from "../../../utils/slugify.js";
import AppComponent from "../lib/AppComponents.js";

class AddTargetButton extends AppComponent {
  template() {
    return /*html*/ `
<button type="button" class="btn btn-primary" id="add-target-btn" aria-label="Add Target" title="Add Target">
    Add Target
</button>
    `;
  }

  afterRender() {
    const button = this.querySelector("#add-target-btn");

   button.addEventListener("click", (event) => {
     event.stopPropagation();
     const countryName = this.getAttribute("country-name");
     const countryId = this.getAttribute("country-id");
     console.log({ countryId, countryName });
     history.pushState(
       { countryId, countryName },
       "",
       `#/targets/${slugify(countryName)}/add-targets`,
     );
     window.dispatchEvent(new HashChangeEvent("hashchange"));
   });
  }
}

customElements.define("add-target-button", AddTargetButton);
