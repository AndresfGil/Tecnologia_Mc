package co.com.tecnologia.model.tecnologia.gateways;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import reactor.core.publisher.Mono;

public interface TecnologiaRepository {

    Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia);
    
    Mono<Boolean> existePorNombre(String nombre);
}
