import AppComponent from '../lib/AppComponents.js';

class Header extends AppComponent {

  template() {
      return /*html*/`
          <nav class="navbar navbar-expand-md bg-body-tertiary">
            <div class="container">
              <a class="navbar-brand" href="/frontend/pages/countries/countries-page.html">
                <img src="/frontend/assets/logo-planetpulse.png" alt="Logo planet pulse" class="d-inline-block">
                <span>Planet<span class="text-pulse">Pulse</span></span>
              </a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                  data-bs-target="#navbarNav" aria-controls="navbarNav"
                  aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-md-auto gap-md-3">
                  <li class="nav-item"><a class="nav-link" href="/frontend/pages/countries/countries-page.html">Countries</a></li>
                  <li class="nav-item"><a class="nav-link" href="/frontend/pages/indicator/add-indicator.html">Indicators</a></li>
                  <li class="nav-item"><a class="nav-link" href="/frontend/pages/targets/targets-page.html">Targets</a></li>
                  <li class="nav-item"><a class="nav-link" href="/frontend/pages/notes/create-note.html">Monitoring note</a></li>
                </ul>
              </div>
            </div>
          </nav>
      `;
  }

  afterRender() {
  this.querySelectorAll('.nav-link').forEach((link) => {
      const href = link.getAttribute('href');
      const isCurrent = href !== '#' && link.pathname === window.location.pathname;
      link.classList.toggle('active', isCurrent);
  });
  
  }
}

customElements.define('app-header', Header);

