
import AppComponent from "../../js/components/lib/AppComponents.js";
import "../../js/components/Notes/NoteForm.js";

class CreateNotePage extends AppComponent {

    template() {
        return /*html*/`
            <note-form></note-form>
        `;
    }
}

customElements.define("create-note-page", CreateNotePage);
