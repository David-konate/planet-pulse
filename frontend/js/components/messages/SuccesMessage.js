import { createAlert } from "./createAlert.js";

export function SuccesMessage(message) {
  return createAlert(message, "success");
}
