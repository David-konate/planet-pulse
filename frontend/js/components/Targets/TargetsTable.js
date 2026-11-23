import AppComponent from "../lib/AppComponents.js";
import { escapeHtml } from "../../../utils/sanitize.js";

// 0 est une valeur valide : on ne teste que null / undefined
const format = (value, unit = "") =>
  value === null || value === undefined ? "N/A" : `${value}${unit}`;

class TargetsTable extends AppComponent {
  template() {
    const { target } = this.props;

    return /*html*/ `
      <ul class="list-group list-group-flush">
        <li class="list-group-item">
          <div class="fw-semibold">CO2</div>
          <div>${escapeHtml(format(target.co2Emissions))}</div>
        </li>
        <li class="list-group-item">
          <div class="fw-semibold">PM2.5</div>
          <div>${escapeHtml(format(target.pm25Emissions))}</div>
        </li>
        <li class="list-group-item">
          <div class="fw-semibold">Forest</div>
          <div>${escapeHtml(format(target.forestArea, " %"))}</div>
        </li>
        <li class="list-group-item">
          <div class="fw-semibold">Energy</div>
          <div>${escapeHtml(format(target.renewableEnergyShare))}</div>
        </li>
      </ul>
    `;
  }
}

customElements.define("targets-table", TargetsTable);
