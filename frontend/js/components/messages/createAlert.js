import { escapeHtml } from "../../../utils/sanitize.js";

export function createAlert(message, type) {
  const alert = document.createElement("div");
  alert.className = `alert alert-${type} alert-dismissible`;
  alert.setAttribute("role", "alert");
  alert.innerHTML = /*html*/ `
    ${escapeHtml(message)}
    <button type="button" class="btn-close" aria-label="Close"></button>`;

  alert
    .querySelector(".btn-close")
    .addEventListener("click", () => alert.remove());

  return alert;
}
