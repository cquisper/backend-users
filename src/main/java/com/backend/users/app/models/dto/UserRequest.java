package com.backend.users.app.models.dto;

public record UserRequest(
        String identificacion,
        String telefono,
        String nombre
) {
}
