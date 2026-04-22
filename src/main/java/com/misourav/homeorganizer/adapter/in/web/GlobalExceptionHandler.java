package com.misourav.homeorganizer.adapter.in.web;

import com.misourav.homeorganizer.domain.exception.EmailAlreadyUsedException;
import com.misourav.homeorganizer.domain.exception.InvalidCredentialsException;
import com.misourav.homeorganizer.domain.exception.NotAMemberException;
import com.misourav.homeorganizer.domain.exception.RoleNotFoundException;
import com.misourav.homeorganizer.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> onValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                fields.put(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.badRequest().body(error(HttpStatus.BAD_REQUEST, "Validation failed", fields));
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Map<String, Object>> onEmailUsed(EmailAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error(HttpStatus.CONFLICT, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> onInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error(HttpStatus.UNAUTHORIZED, ex.getMessage(), null));
    }

    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class})
    public ResponseEntity<Map<String, Object>> onNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(HttpStatus.NOT_FOUND, ex.getMessage(), null));
    }

    @ExceptionHandler(NotAMemberException.class)
    public ResponseEntity<Map<String, Object>> onNotMember(NotAMemberException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error(HttpStatus.FORBIDDEN, ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> onDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error(HttpStatus.FORBIDDEN, "Insufficient permissions" + ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> onIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(error(HttpStatus.BAD_REQUEST, ex.getMessage(), null));
    }

    private Map<String, Object> error(HttpStatus status, String message, Map<String, String> fields) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (fields != null) body.put("fields", fields);
        return body;
    }
}
