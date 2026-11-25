// import the API base form URL from the configuration fil
import { API_BASE_URL } from "../config.js";

// send a new note to the backend
export async function createNote(noteData) {

    // send an HTTP POST request to the notes endpoint 
    const response = await fetch(`${API_BASE_URL}/notes`, {

        // define the HTTP method 
        method: "POST",

        // specify that the request body contains JSON data
        headers: {"Content-Type": "application/json"},

        // convert the JS object into a JSON string
        body: JSON.stringify(noteData)
    });

    // check if the HTTP response indicate an error
    if (!response.ok) {
        throw new Error(`Unable to create note (HTTP ${response.status})`);
    }

    // convert the JSON response into a JS object
    return response.json();
}
