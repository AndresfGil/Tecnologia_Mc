package co.com.tecnologia.api.docs;

import co.com.tecnologia.api.TecnologiaHandler;
import co.com.tecnologia.api.dto.TecnologiaBatchRequestDto;
import co.com.tecnologia.api.dto.TecnologiaBatchResponseDto;
import co.com.tecnologia.api.dto.TecnologiaRequestDto;
import co.com.tecnologia.api.dto.TecnologiaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface TecnologiaControllerDocs {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/tecnologia",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.POST,
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "listenGuardarTecnologia",
                    operation = @Operation(
                            operationId = "createTecnologia",
                            summary = "Guardar tecnología",
                            description = "Crea una nueva tecnología en el sistema",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = TecnologiaRequestDto.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Tecnología creada exitosamente",
                                            content = @Content(schema = @Schema(implementation = TecnologiaResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Error de validación en los datos enviados"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno del servidor"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/tecnologia/batch",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.POST,
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "listenObtenerTecnologiasPorIds",
                    operation = @Operation(
                            operationId = "obtenerTecnologiasPorIds",
                            summary = "Obtener tecnologías por IDs (Batch)",
                            description = "Consulta múltiples tecnologías por sus IDs de forma no bloqueante. Retorna solo el id y nombre de cada tecnología.",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = TecnologiaBatchRequestDto.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Lista de tecnologías obtenida exitosamente",
                                            content = @Content(schema = @Schema(implementation = TecnologiaBatchResponseDto.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Error de validación en los datos enviados"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno del servidor"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(TecnologiaHandler handler);
}
