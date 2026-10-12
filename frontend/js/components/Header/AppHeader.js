import AppComponent from '../lib/AppComponents.js';

class Header extends AppComponent {

    template() {
        return /*html*/`
            <nav class="navbar navbar-expand-md bg-body-tertiary">
              <div class="container">
                <a class="navbar-brand" href="/pages/CountriesPage/">
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
                    <li class="nav-item"><a class="nav-link" href="/pages/CountriesPage/">Countries</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Indicators</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Monitoring note</a></li>
                  </ul>
                </div>
              </div>
            </nav>
        `;
    }

    // Marque le lien de la page courante comme actif
    // afterRender() {
    //     this.querySelectorAll('.nav-link').forEach((link) => {
    //         const isCurrent = link.pathname === window.location.pathname;
    //         link.classList.toggle('active', isCurrent);
    //         if (isCurrent) link.setAttribute('aria-current', 'page');
    //     });

        afterRender() {
        this.querySelectorAll('.nav-link').forEach((link) => {
            const href = link.getAttribute('href');
            const isCurrent = href !== '#' && link.pathname === window.location.pathname;
            link.classList.toggle('active', isCurrent);
        });
    
    }
}

customElements.define('app-header', Header);

