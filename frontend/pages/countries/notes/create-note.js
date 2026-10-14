import AppComponents from "../../../js/components/lib/AppComponents.js"

class MonitoringNoteForm extends AppComponents {
    constructor() {
        super();
    }
    template() {
        return `
        <main class="container py-4">
<div class="form-field" id="form-content">
<form id="note-form" >    
         <div class="mb-3">
          <label for="title">Title</label>
          <input type="text" class="form-control" id="note-title" name="note_title" placeholder="Enter title">
        </div>
        <div class="mb-3">
          <label for="date">Date</label>
          <input type="date" class="form-control" name="form_control" id="note-date">
        </div>
        <div class="mb-3">
          <label for="content">Text</label>
          <input type="text" class="form-control" id="note-content" name="note_content" placeholder="Content">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
</form>
</div>
      </main>
    `;
    }
    async afterRender() {
        // connection aux élements HTML
        const title = this.querySelector("#note-title");
        const date = this.querySelector("#note-date");
        const content = this.querySelector("#note-content");
        const form = this.querySelector("#note-form");

        title.minLength = 1;
        title.maxLength = 3;

        form.minLength =
            form.maxLength =




            form.addEventListener("submit", (event) => {
                event.preventDefault();

                const formData = new FormData(form);
                const noteData = {
                    // transfert de la valeurs contenu dans la constante dans le formulaire 
                    // construction + transfert du contenu de la const
                    title_input: formData.get("note_title"),


                };
                console.log("form soumis:", noteData);


            });
    }
}

customElements.define("monitoring-note-form", MonitoringNoteForm); 