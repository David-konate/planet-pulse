import { API_BASE_URL } from '../config.js';

const API_URL = `${API_BASE_URL}/indicators`;

// Sends the new indicator values to the backend.
// Throws an Error with a message that can be shown to the user.
export async function addIndicator(data) {
    let response;
    try {
        response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
    } catch {
        // The request did not reach the server (server stopped, CORS, network...)
        throw new Error('The server is not reachable. Please try again later.');
    }

    if (!response.ok) {
        // The backend answers { "message": "..." } for every error
        const body = await response.json().catch(() => ({}));
        throw new Error(body.message || 'The values could not be saved. Please try again.');
    }

    return response.json();
}

// Gets the list of indicators (field name, label, unit, maximum) to build the form.
export async function getIndicatorTypes() {
    let response;
    try {
        response = await fetch(`${API_URL}/types`);
    } catch {
        throw new Error('The server is not reachable. Please try again later.');
    }

    if (!response.ok) {
        throw new Error('The indicators could not be loaded. Please try again.');
    }

    return response.json();
}
