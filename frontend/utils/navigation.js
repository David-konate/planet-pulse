// utils/navigation.js
import { slugify } from "./slugify.js";

const INDEX_URL = new URL("../index.html", import.meta.url);
const KEY = "countryContext";

export function goToTargets({ countryIso, countryName }) {
  sessionStorage.setItem(KEY, JSON.stringify({ countryIso, countryName }));
  location.assign(new URL(`#/countries/${countryIso.toLowerCase()}/targets`, INDEX_URL));
}

export function readCountryContext(iso) {
  try {
    const ctx = JSON.parse(sessionStorage.getItem(KEY));
    return ctx && ctx.countryIso.toLowerCase() === iso.toLowerCase() ? ctx : null;
  } catch {
    return null;
  }
}
