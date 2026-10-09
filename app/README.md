# Mon projet

1. Installer Bootstrap : `npm install`
   (ou copier ton dossier `node_modules/bootstrap` existant à la racine)
2. Lancer un serveur local à la racine du projet :
   - extension VS Code **Live Server** (clic droit sur index.html > Open with Live Server)
   - ou `npm start`
   - ou `python -m http.server 8000`
3. Ouvrir http://localhost:8000 (ou l'adresse donnée par Live Server)

Ne pas ouvrir index.html en double-clic : fetch() et import() sont bloqués en file://.
