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
  return result;
}

export async function getTargetsBycountryIso(countryIso) {
  const response = await fetch(`${API_BASE_URL}/targets/country/${countryIso}`);

  if (!response.ok) {
    const body = await response.json().catch(() => null);
    throw new Error(body?.message ?? "Unexpected error.");
  }

  return response.json();
}

