import { createAlert } from "./createAlert.js";

export function ErrorMessage(message) {
  return createAlert(message, "danger");
}