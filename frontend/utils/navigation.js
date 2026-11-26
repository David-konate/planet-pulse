// utils/navigation.js
import { slugify } from "./slugify.js";

const INDEX_URL = new URL("../index.html", import.meta.url);
const KEY = "countryContext";

export function readCountryContext(slug) {
  try {
    const ctx = JSON.parse(sessionStorage.getItem(KEY));
    return ctx && slugify(ctx.countryName) === slug ? ctx : null;
  } catch {
    return null;
  }
}

export function goToTargets({ countryId, countryName }) {
  const hash = `#/countries/${slugify(countryName)}/targets`;

  sessionStorage.setItem(KEY, JSON.stringify({ countryId, countryName }));

  if (document.getElementById("app")) {
    location.hash = hash; // déclenche hashchange tout seul, plus besoin de dispatchEvent
  } else {
    location.assign(`${INDEX_URL.pathname}${hash}`);
  }
}
