// retrieve class AppComponents create in an other file
import AppComponents from "../../js/components/lib/AppComponents.js"

// defining a new component call MonitorinfNoteForm and display html
class MonitoringNoteForm extends AppComponents {

    template() {
        return /*html*/ `
        <main class="container py-4">
           <h1>Create a Note</h1> 
            <div class="form-field" id="form-content">

                <form id="note-form">
                    <div class="mb-3">
                        <label for="note-title" class="form-label">Title *</label>
                        <input type="text" class="form-control" id="note-title" name="note_title" placeholder="Enter title" required>
                    </div>
                    <div class="mb-3">
                        <label for="note-date" class="form-label">Date *</label>
                        <!--<input type="date" class="form-control" id="note-date" name="note_date" required> -->
                        <input type="text" class="form-control" id="note-date" name="note_date" placeholder="MM/DD/YYYY">
                    </div>
                    <div class="mb-3">
                        <label for="note-content" class="form-label">Text *</label>
                        <input type="text" class="form-control" id="note-content" name="note_content" placeholder="Content" required>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>
        </main>
        `;
    }
    // retrieve elements of the form and prepare their behavior
    async afterRender() {
        // connection to HTML elements
        const title = this.querySelector("#note-title");
        const date = this.querySelector("#note-date");
        const content = this.querySelector("#note-content");
        const form = this.querySelector("#note-form");

        // default value: today's date  
        //date.value = new Date().toISOString().split("T")[0];
        date.value = new Date().toLocaleDateString("en-US");

        // constrenght
        title.minLength = 1;
        title.maxLength = 255;
        content.minLength = 1;
        content.maxLength = 10000;

        // intercept the submit and retrieve input
        form.addEventListener("submit", (event) => {
            // not allowing normal reload
            event.preventDefault();
            // check if the date field is empty and ask the user if they want to continue with today's date
            if (date.value === "") {
                const continueWithToday = confirm(
                    "Please enter a date or click OK to continue with today's date."
                );
                if (continueWithToday) {
                    date.value = new Date().toLocaleDateString("en-US");
                } else {
                    return; // stop the form submission
                }
            }
            // retrieve the data from the form
            const formData = new FormData(form);
            // create an object to store the data
            const noteData = {
                //Transferring the value contained in the constant to the form
                // Construction + transferring the content of the const
                title_input: formData.get("note_title"),
                date_input: formData.get("note_date"),
                content_input: formData.get("note_content"),
            };
            console.log("form submited:", noteData);
        });
    }
}
// recording HTML element
customElements.define("monitoring-note-form", MonitoringNoteForm);