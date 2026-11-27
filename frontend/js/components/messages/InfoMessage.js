import { createAlert } from "./createAlert.js";

export function InfoMessage(message) {
  return createAlert(message, "info");
}
