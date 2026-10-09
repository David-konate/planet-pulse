export async function loadComponent(name, selector) {
  const target = document.querySelector(selector);
  if (!target) {
    console.error(`Conteneur introuvable : ${selector}`);
    return;
  }

  const base = `components/${name}/${name}`;

  // 1. CSS (ajouté une seule fois)
  if (!document.querySelector(`link[data-component="${name}"]`)) {
    const link = document.createElement("link");
    link.rel = "stylesheet";
    link.href = `${base}.css`;
    link.dataset.component = name;
    document.head.appendChild(link);
  }

  // 2. HTML
  try {
    const response = await fetch(`${base}.html`);
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    target.innerHTML = await response.text();
  } catch (error) {
    console.error(`Impossible de charger ${base}.html`, error);
    return;
  }

  // 3. JS du composant (optionnel)
  let module;
  try {
    module = await import(new URL(`${base}.js`, document.baseURI).href);
  } catch (error) {
    console.warn(`Pas de JS chargé pour "${name}"`, error);
    return;
  }

  module.init?.(target);
}

document.addEventListener("DOMContentLoaded", () => {
  loadComponent("header", "#header");
});
