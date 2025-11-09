package co.com.tecnologia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta simplificado con id y nombre de la tecnología")
public record TecnologiaBatchResponseDto(
        @Schema(description = "Identificador único de la tecnología", example = "1")
        Long id,

        @Schema(description = "Nombre de la tecnología", example = "Java")
        String nombre
) {
}

