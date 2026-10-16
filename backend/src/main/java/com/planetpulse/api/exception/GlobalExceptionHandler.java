package com.planetpulse.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

/**
 * Gestionnaire d'erreurs global.
 *
 * @RestControllerAdvice fait que cette classe s'applique à TOUS les contrôleurs
 * de l'application. Dès qu'une exception remonte d'un contrôleur ou d'un service,
 * Spring cherche ici la méthode @ExceptionHandler correspondante et renvoie
 * son résultat au client en JSON.
 *
 * Résultat : les contrôleurs n'ont aucun try/catch, et toutes les erreurs
 * ont le même format (ErrorResponse).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Logger pour écrire les erreurs dans la console / les logs du serveur
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Cas 404 : une ressource précise n'existe pas.
     * Déclenché quand le service fait : throw new ResourceNotFoundException("...")
     * Le message de l'exception est renvoyé tel quel au client.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Cas 404 : la route elle-même n'existe pas (ex. GET /api/nimportequoi).
     * Sans ce handler, le handler générique plus bas la transformerait en 500.
     * (NoResourceFoundException existe à partir de Spring Boot 3.2.)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResource(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Resource not found");
    }

    /**
     * Cas 400 : un paramètre n'a pas le bon type.
     * Exemple : GET /api/targets/abc alors que l'id attendu est un Long.
     * ex.getName() donne le nom du paramètre fautif (ici "id").
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadParam(MethodArgumentTypeMismatchException ex) {
        return build(HttpStatus.BAD_REQUEST, "Invalid parameter: " + ex.getName());
    }

    /**
     * Filet de sécurité : toute exception non prévue ci-dessus tombe ici (500).
     * On loggue le détail complet côté serveur pour pouvoir déboguer,
     * mais on renvoie un message générique au client, pour ne pas exposer
     * d'informations internes (noms de classes, requêtes SQL, etc.).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Internal error", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error occurred");
    }

    /**
     * Méthode utilitaire : construit la réponse HTTP avec le bon code de statut
     * et un corps ErrorResponse (code, message, date de l'erreur).
     * Évite de répéter ce code dans chaque handler.
     */
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), message, Instant.now()));
    }
}