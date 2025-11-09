package co.com.tecnologia.usecase.tecnologia;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import co.com.tecnologia.model.tecnologia.exception.ValidarNombreExistente;
import co.com.tecnologia.model.tecnologia.gateways.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TecnologiaUseCase {

    private final TecnologiaRepository tecnologiaRepository;

    public Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia) {
        return tecnologiaRepository.existePorNombre(tecnologia.getNombre())
                .flatMap(existe -> Boolean.TRUE.equals(existe)
                        ? Mono.error(new ValidarNombreExistente(tecnologia.getNombre()))
                        : tecnologiaRepository.guardarTecnologia(tecnologia));
    }

    public Flux<Tecnologia> obtenerTecnologiasPorIds(List<Long> ids) {
        return tecnologiaRepository.obtenerTecnologiasPorIds(ids);
    }
}
