// frontend/js/router.js
const routes = [
  {
    pattern: /^#\/countries\/([a-z]{2})\/targets$/i,
    load: () => import("../pages/countries/country-targets/targets-page.js"),
    tag: "targets-page",
  },
  {
    pattern: /^#\/countries\/([a-z]{2})$/i,
    load: () => import("../pages/countries/country-targets/targets-page.js"),
    tag: "country-page",
  },
  {
    pattern: /^#\/countries\/?$/,
    load: () => import("../pages/countries/countries-page.js"),
    tag: "countries-page",
  },

{
  pattern: /^#\/targets\/([a-z]{2})\/add-targets$/,
  load: () => import("../pages/targets/add-targets/add-targets-page.js"),
  tag: "add-targets-page",
},
{
  pattern: /^#\/targets\/([a-z]{2})\/edit-targets$/,
  load: () => import("../pages/targets/edit-targets/edit-targets-page.js"),
  tag: "edit-targets-page",
},
];

const DEFAULT_ROUTE = "#/countries";
const outlet = document.getElementById("app");

let renderId = 0; // identifie le rendu en cours

function showMessage(text) {
  const container = document.createElement("div");
  container.className = "container py-4";

  const alert = document.createElement("div");
  alert.className = "alert alert-warning";
  alert.setAttribute("role", "alert");
  alert.textContent = text; // text only, never parsed as HTML

  const link = document.createElement("a");
  link.href = DEFAULT_ROUTE;
  link.className = "btn btn-link px-0";
  link.textContent = "← Back to countries";

  container.append(alert, link);
  outlet.replaceChildren(container);
}

async function renderRoute() {
  // URL vide : on normalise vers la route par défaut (replace = pas d'entrée d'historique)
  if (!location.hash || location.hash === "#") {
    location.replace(DEFAULT_ROUTE); // déclenche hashchange, qui rappelle renderRoute
    return;
  }

  const currentRender = ++renderId;
  const hash = location.hash;

  for (const route of routes) {
    const match = hash.match(route.pattern);
    if (!match) continue;

    try {
      await route.load(); // exécute customElements.define
    } catch (error) {
      console.error(`Router: unable to load "${route.tag}"`, error);
      if (currentRender === renderId) showMessage("This page is unavailable.");
      return;
    }

    // l'utilisateur a navigué ailleurs pendant le chargement : on abandonne
    if (currentRender !== renderId) return;

    const el = document.createElement(route.tag);
    if (match[1]) el.dataset.param = match[1]; // le slug ou l'id
    outlet.replaceChildren(el);
    window.scrollTo(0, 0);
    return;
  }

  showMessage("Page not found.");
}

window.addEventListener("hashchange", renderRoute);
renderRoute(); // premier affichage
