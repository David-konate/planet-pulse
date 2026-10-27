import { API_BASE_URL } from "../config.js";

export async function createTarget(target) {
  const response = await fetch(`${API_BASE_URL}/targets`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(target),
  });
  if (!response.ok) {
    throw new Error(`Unable to create target (HTTP ${response.status})`);
  }
  const result = await response.json();
  console.log("Target created:", result);
  return result;
}
