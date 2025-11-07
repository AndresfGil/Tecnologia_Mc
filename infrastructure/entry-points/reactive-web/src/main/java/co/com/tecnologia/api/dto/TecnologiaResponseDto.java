package co.com.tecnologia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta con los datos de la tecnología creada")
public record TecnologiaResponseDto(
        @Schema(description = "Identificador único de la tecnología", example = "1")
        Long idTecnologia,
        
        @Schema(description = "Nombre de la tecnología", example = "Java")
        String nombre,
        
        @Schema(description = "Descripción de la tecnología", example = "Lenguaje de programación orientado a objetos")
        String descripcion
) {
}

