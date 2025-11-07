package co.com.tecnologia.r2dbc;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import co.com.tecnologia.model.tecnologia.gateways.TecnologiaRepository;
import co.com.tecnologia.r2dbc.entities.TecnologiaEntity;
import co.com.tecnologia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

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
    public Mono<Boolean> existePorNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }
}
