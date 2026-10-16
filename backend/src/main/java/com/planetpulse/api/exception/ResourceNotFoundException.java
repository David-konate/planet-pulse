package com.planetpulse.api.exception;

/**
 * Exception métier : lancée quand une ressource demandée n'existe pas
 * (ex. un Target avec un id inconnu).
 *
 * Elle étend RuntimeException (exception non vérifiée), donc pas besoin
 * de la déclarer avec "throws" dans les signatures de méthodes.
 * C'est GlobalExceptionHandler qui l'intercepte et la transforme en réponse HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param message le message qui sera renvoyé au client dans ErrorResponse
     *                (ex. "Target introuvable avec l'id 999")
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}