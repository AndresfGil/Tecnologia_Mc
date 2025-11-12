package co.com.tecnologia.api;

import co.com.tecnologia.api.dto.TecnologiaBatchRequestDto;
import co.com.tecnologia.api.dto.TecnologiaRequestDto;
import co.com.tecnologia.api.helpers.DtoValidator;
import co.com.tecnologia.api.helpers.TecnologiaMapper;
import co.com.tecnologia.usecase.tecnologia.TecnologiaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TecnologiaHandler {

    private final TecnologiaUseCase tecnologiaUseCase;
    private final DtoValidator dtoValidator;
    private final TecnologiaMapper tecnologiaMapper;

    public Mono<ServerResponse> listenGuardarTecnologia(ServerRequest req) {
        return req.bodyToMono(TecnologiaRequestDto.class)
                .flatMap(dto -> dtoValidator.validate(dto)
                        .map(tecnologiaMapper::toDomain)
                        .flatMap(tecnologiaUseCase::guardarTecnologia)
                        .map(tecnologiaMapper::toResponseDto)
                        .flatMap(responseDto -> ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseDto)));
    }

    public Mono<ServerResponse> listenObtenerTecnologiasPorIds(ServerRequest req) {
        return req.bodyToMono(TecnologiaBatchRequestDto.class)
                .flatMap(dto -> dtoValidator.validate(dto)
                        .flatMapMany(dtoValidado -> tecnologiaUseCase.obtenerTecnologiasPorIds(dtoValidado.ids()))
                        .map(tecnologiaMapper::toBatchResponseDto)
                        .collectList()
                        .flatMap(responseList -> ServerResponse
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseList)));
    }

    public Mono<ServerResponse> listenActivarTecnologias(ServerRequest req) {
        return req.bodyToMono(TecnologiaBatchRequestDto.class)
                .flatMap(dto -> dtoValidator.validate(dto)
                        .flatMap(dtoValidado -> tecnologiaUseCase.activarTecnologias(dtoValidado.ids()))
                        .then(ServerResponse
                                .status(HttpStatus.NO_CONTENT)
                                .build()));
    }

    public Mono<ServerResponse> listenDesactivarTecnologias(ServerRequest req) {
        return req.bodyToMono(TecnologiaBatchRequestDto.class)
                .flatMap(dto -> dtoValidator.validate(dto)
                        .flatMap(dtoValidado -> tecnologiaUseCase.desactivarTecnologias(dtoValidado.ids()))
                        .then(ServerResponse
                                .status(HttpStatus.NO_CONTENT)
                                .build()));
    }
}
