package co.com.tecnologia.r2dbc;

import co.com.tecnologia.r2dbc.entities.TecnologiaEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface TecnologiaReactiveRepository extends ReactiveCrudRepository<TecnologiaEntity, Long>, ReactiveQueryByExampleExecutor<TecnologiaEntity> {

    Mono<Boolean> existsByNombre(String nombre);
    
    Flux<TecnologiaEntity> findByIdIn(Collection<Long> ids);
}
