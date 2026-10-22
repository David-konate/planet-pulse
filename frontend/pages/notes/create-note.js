// Import the AppComponents class
import AppComponents from "../../js/components/lib/AppComponents.js";

// Define the MonitoringNoteForm component
class MonitoringNoteForm extends AppComponents {

    template() {
        return /*html*/ `
            <main class="container py-4">
                <h1>Create a Note</h1>

                <div class="form-field" id="form-content">
                    <form id="note-form">

                        <div class="mb-3">
                            <label for="note-title" class="form-label">Title *</label>
                            <input
                                type="text"
                                class="form-control"
                                id="note-title"
                                name="note_title"
                                placeholder="Enter title"
                                autocomplete="off"
                                required
                            >
                        </div>

                        <div class="mb-3">
                            <label for="note-date" class="form-label">Date</label>
                            <input
                                type="text"
                                class="form-control"
                                id="note-date"
                                name="note_date"
                                placeholder="MM-DD-YYYY"
                                pattern="\\d{2}-\\d{2}-\\d{4}"
                            >
                        </div>

                        <div class="mb-3">
                            <label for="note-content" class="form-label">Text *</label>
                            <textarea
                                class="form-control"
                                id="note-content"
                                name="note_content"
                                placeholder="Content"
                                style="height: 250px; padding-top: 10px;"
                                autocomplete="off"
                                required
                            ></textarea>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">
                                Submit
                            </button>
                        </div>

                    </form>
                </div>
            </main>
        `;
    }

    // Retrieve form elements and prepare their behavior
    async afterRender() {

        // Connect to HTML elements
        const form = this.querySelector("#note-form");
        const title = this.querySelector("#note-title");
        const dateInput = this.querySelector("#note-date");
        const content = this.querySelector("#note-content");

        // Set field constraints
        title.minLength = 1;
        title.maxLength = 255;

        content.minLength = 1;
        content.maxLength = 10000;

        // Validate the date format: MM-DD-YYYY
        const dateRegex = /^\d{2}-\d{2}-\d{4}$/;
        dateInput.pattern = dateRegex.source;

        // Automatically add dashes while typing the date
        dateInput.addEventListener("input", () => {

            // Keep only numbers
            let value = dateInput.value.replace(/\D/g, "");

            // Limit to 8 digits
            value = value.slice(0, 8);

            // Add dashes automatically
            if (value.length > 4) {
                value = value.slice(0, 2) + "-" +
                    value.slice(2, 4) + "-" +
                    value.slice(4);
            } else if (value.length > 2) {
                value = value.slice(0, 2) + "-" +
                    value.slice(2);
            }

            dateInput.value = value;
        });

        // Handle form submission
        form.addEventListener("submit", (event) => {

            // Prevent the default page reload
            event.preventDefault();

            // Retrieve form data
            const formData = new FormData(form);

            let date = formData.get("note_date");

            // If the date is empty, use today's date in MM-DD-YYYY format
            if (!date) {
                const today = new Date();

                const month = String(today.getMonth() + 1).padStart(2, "0");
                const day = String(today.getDate()).padStart(2, "0");
                const year = today.getFullYear();

                date = `${month}-${day}-${year}`;
            }

            // Create an object containing the note data
            const noteData = {
                title_input: formData.get("note_title"),
                date_input: date,
                content_input: formData.get("note_content"),
            };

            console.log("Form submitted:", noteData);
        });
    }
}

// Register the custom HTML element
customElements.define("monitoring-note-form", MonitoringNoteForm);
