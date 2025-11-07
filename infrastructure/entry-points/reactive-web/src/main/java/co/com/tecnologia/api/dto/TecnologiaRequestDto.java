package co.com.tecnologia.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para crear una nueva tecnología")
public record TecnologiaRequestDto(
        @Schema(description = "Nombre de la tecnología", example = "Java", required = true, maxLength = 50)
        @NotNull(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
        String nombre,

        @Schema(description = "Descripción de la tecnología", example = "Lenguaje de programación orientado a objetos", required = true, maxLength = 90)
        @NotNull(message = "La descripción es obligatoria")
        @Size(max = 90, message = "La descripción no puede exceder 90 caracteres")
        String descripcion
) {
}
