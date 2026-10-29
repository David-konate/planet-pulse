import { createAlert } from "./createAlert.js";

export function SuccessMessage(message) {
  return createAlert(message, "success");
}
