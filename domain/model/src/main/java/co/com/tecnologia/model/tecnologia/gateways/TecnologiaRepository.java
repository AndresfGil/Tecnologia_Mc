package co.com.tecnologia.model.tecnologia.gateways;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface TecnologiaRepository {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);

    Flux<Tecnologia> obtenerTecnologiasPorIds(List<Long> ids);
    
    Mono<Boolean> existePorNombre(String nombre);
    
    Mono<Void> activarTecnologias(List<Long> ids);
    
    Mono<Void> desactivarTecnologias(List<Long> ids);
    
    Mono<Void> eliminarInactivasAntiguas(LocalDateTime fechaLimite);
}
