import AppComponent from "../lib/AppComponents.js";
import { escapeHtml } from "../../../utils/sanitize.js";
import { InfoMessage } from "../messages/InfoMessage.js";

// 0 est une valeur valide : on ne teste que null / undefined
const format = (value, unit = "") =>
  value === null || value === undefined ? "N/A" : `${value}${unit}`;

const definition = {
  co2Emissions:
    "Unité de masse égale à 1 000 kilogrammes. Elle mesure la quantité totale de dioxyde de carbone émise dans l'atmosphère par les activités du pays (énergie, transport, industrie) sur une période donnée.", 
  pm25Emissions:
    "Unité de concentration qui mesure la masse de particules fines (diamètre inférieur à 2,5 micromètres) présente dans un mètre cube d'air. Plus cette valeur est basse, meilleure est la qualité de l'air.",
  forestArea:
    "Unité de superficie qui mesure la proportion de la superficie terrestre totale du pays recouverte par des forêts. Cet indicateur permet de suivre la déforestation ou les efforts de reboisement.",
  renewableEnergyShare:
    "Part que représentent les énergies renouvelables (solaire, éolien, hydraulique, etc.) dans la consommation ou la production totale d'énergie du pays."
};

class TargetsTable extends AppComponent {
  template() {
    const { target } = this.props;
    const idTitre = `target-${target.targetYear}`;
    const idCollapse = `collapse-${target.targetYear}`;
    const open = this.props.open ?? false;
    console.log(this.props)


    return /*html*/ `

      <div class="accordion-item">
        <h2 class="accordion-header mb-1">
          <button id="${idTitre}" class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#${idCollapse}" aria-expanded="${open}" aria-controls="${idCollapse}">
            <span class="flex-grow-1 text-center fw-bold">${escapeHtml(String(target.targetYear))}</span>  
          </button>
        </h2>
          <div id="${idCollapse}" class="accordion-collapse collapse ${open ? "show" : ""}" aria-labelledby="${idTitre}">
            <div class="accordion-body">
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <div class="fw-semibold">CO2</div>
                  <div class="d-flex justify-content-center align-items-center gap-1 p-2">
                    <div>${escapeHtml(format(target.co2Emissions))}</div>
                    <button type="button" data-value="co2Emissions"  class="input-group-text">t</button>
                  </div>
                </li>
                <li class="list-group-item">
                  <div class="fw-semibold">PM2.5</div>
                   <div class="d-flex justify-content-center align-items-center gap-1 p-2">
                  <div>${escapeHtml(format(target.pm25Emissions))}</div>
                  <button type="button" data-value="pm25Emissions"  class="input-group-text">μg/m³</button>
                  </div>
                </li>
                <li class="list-group-item">
                  <div class="fw-semibold">Forest</div>
                   <div class="d-flex justify-content-center align-items-center gap-1 p-2">
                  <div>${escapeHtml(format(target.forestArea))}</div>
                  <button type="button" data-value="forestArea"  class="input-group-text">%</button>
                  </div>
                </li>
                <li class="list-group-item">
                  <div class="fw-semibold">Energy</div>
                   <div class="d-flex justify-content-center align-items-center gap-1 p-2">
                  <div>${escapeHtml(format(target.renewableEnergyShare))}</div>
                  <button type="button" data-value="renewableEnergyShare"  class="input-group-text">%</button>
                  </div>
                </li>
              </ul>
            </div>
          </div>
      </div>
        
 


    `;
  }
  afterRender() {

    this.querySelectorAll("[data-value]").forEach((button) => {
      button.addEventListener("click", () => {
        const value = button.dataset.value;
        const definitionText = definition[value] ?? "No definition available.";
            const infoValues = document.querySelector("#infos-values");
            infoValues.replaceChildren(InfoMessage(definitionText));    
      });
    });

  }
}


customElements.define("targets-table", TargetsTable);
