package com.backend.users.app.models.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String identificacion,
        String telefono,
        String nombre
) {
}
