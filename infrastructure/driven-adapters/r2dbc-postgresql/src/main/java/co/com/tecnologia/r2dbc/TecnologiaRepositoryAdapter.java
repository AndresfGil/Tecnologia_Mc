package co.com.tecnologia.r2dbc;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import co.com.tecnologia.model.tecnologia.gateways.TecnologiaRepository;
import co.com.tecnologia.r2dbc.entities.TecnologiaEntity;
import co.com.tecnologia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class TecnologiaRepositoryAdapter extends ReactiveAdapterOperations<
        Tecnologia,
        TecnologiaEntity,
        Long,
        TecnologiaReactiveRepository
> implements TecnologiaRepository {
    public TecnologiaRepositoryAdapter(TecnologiaReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Tecnologia.class));
    }

    @Override
    public Mono<Tecnologia> guardarTecnologia(Tecnologia tecnologia) {
        return super.save(tecnologia);
    }

    @Override
    public Flux<Tecnologia> obtenerTecnologiasPorIds(List<Long> ids) {
        return repository.findByIdIn(ids)
                .map(entity -> mapper.map(entity, Tecnologia.class));
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }

    @Override
    public Mono<Void> activarTecnologias(List<Long> ids) {
        return repository.findByIdIn(ids)
                .map(entity -> entity.toBuilder().activa(true).build())
                .collectList()
                .flatMap(entities -> repository.saveAll(entities).then());
    }

    @Override
    public Mono<Void> desactivarTecnologias(List<Long> ids) {
        return repository.findByIdIn(ids)
                .map(entity -> entity.toBuilder().activa(false).build())
                .collectList()
                .flatMap(entities -> repository.saveAll(entities).then());
    }

    @Override
    public Mono<Void> eliminarInactivasAntiguas(java.time.LocalDateTime fechaLimite) {
        return repository.findAll()
                .filter(entity -> Boolean.FALSE.equals(entity.getActiva()))
                .filter(entity -> entity.getFechaModificacion() != null && 
                                 entity.getFechaModificacion().isBefore(fechaLimite))
                .flatMap(entity -> repository.deleteById(entity.getId()))
                .then();
    }
}
