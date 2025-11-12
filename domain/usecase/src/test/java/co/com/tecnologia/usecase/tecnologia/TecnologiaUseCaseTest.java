package co.com.tecnologia.usecase.tecnologia;

import co.com.tecnologia.model.tecnologia.Tecnologia;
import co.com.tecnologia.model.tecnologia.exception.ValidarNombreExistente;
import co.com.tecnologia.model.tecnologia.gateways.TecnologiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TecnologiaUseCaseTest {

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @InjectMocks
    private TecnologiaUseCase tecnologiaUseCase;

    private Tecnologia tecnologia;

    @BeforeEach
    void setUp() {
        tecnologia = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .activa(true)
                .build();
    }

    @Test
    void guardarTecnologia_CuandoNombreNoExiste_DeberiaGuardarExitosamente() {
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.just(false));
        when(tecnologiaRepository.guardarTecnologia(any(Tecnologia.class))).thenReturn(Mono.just(tecnologia));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void guardarTecnologia_CuandoNombreExiste_DeberiaLanzarValidarNombreExistente() {
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectErrorMatches(throwable -> throwable instanceof ValidarNombreExistente
                        && throwable.getMessage().equals("El nombre de la tecnología ya está registrado"))
                .verify();
    }

    @Test
    void guardarTecnologia_CuandoNombreExisteConBooleanTrue_DeberiaLanzarValidarNombreExistente() {
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectErrorMatches(throwable -> throwable instanceof ValidarNombreExistente)
                .verify();
    }

    @Test
    void guardarTecnologia_CuandoNombreNoExisteConBooleanFalse_DeberiaGuardarExitosamente() {
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.just(Boolean.FALSE));
        when(tecnologiaRepository.guardarTecnologia(any(Tecnologia.class))).thenReturn(Mono.just(tecnologia));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void guardarTecnologia_CuandoExistePorNombreRetornaError_DeberiaPropagarError() {
        RuntimeException error = new RuntimeException("Error de conexión");
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.error(error));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void guardarTecnologia_CuandoGuardarTecnologiaRetornaError_DeberiaPropagarError() {
        RuntimeException error = new RuntimeException("Error al guardar");
        when(tecnologiaRepository.existePorNombre(anyString())).thenReturn(Mono.just(false));
        when(tecnologiaRepository.guardarTecnologia(any(Tecnologia.class))).thenReturn(Mono.error(error));

        StepVerifier.create(tecnologiaUseCase.guardarTecnologia(tecnologia))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoExistenTecnologias_DeberiaRetornarFluxConTecnologias() {
        Tecnologia tecnologia1 = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .activa(true)
                .build();
        Tecnologia tecnologia2 = Tecnologia.builder()
                .id(2L)
                .nombre("Python")
                .descripcion("Lenguaje interpretado")
                .activa(true)
                .build();
        List<Long> ids = Arrays.asList(1L, 2L);

        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologia1, tecnologia2));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectNext(tecnologia1)
                .expectNext(tecnologia2)
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoHayTecnologiasInactivas_DeberiaFiltrarlas() {
        Tecnologia tecnologiaActiva = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .activa(true)
                .build();
        Tecnologia tecnologiaInactiva = Tecnologia.builder()
                .id(2L)
                .nombre("Python")
                .descripcion("Lenguaje interpretado")
                .activa(false)
                .build();
        List<Long> ids = Arrays.asList(1L, 2L);

        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologiaActiva, tecnologiaInactiva));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectNext(tecnologiaActiva)
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoListaVacia_DeberiaRetornarFluxVacio() {
        List<Long> ids = Collections.emptyList();
        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.empty());

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoNoExistenTecnologias_DeberiaRetornarFluxVacio() {
        List<Long> ids = Arrays.asList(999L, 1000L);
        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.empty());

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoUnSoloId_DeberiaRetornarUnaTecnologia() {
        List<Long> ids = Collections.singletonList(1L);
        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologia));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectNext(tecnologia)
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoTecnologiaInactiva_DeberiaFiltrarla() {
        Tecnologia tecnologiaInactiva = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .activa(false)
                .build();
        List<Long> ids = Collections.singletonList(1L);

        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologiaInactiva));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .verifyComplete();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoRepositorioRetornaError_DeberiaPropagarError() {
        List<Long> ids = Arrays.asList(1L, 2L);
        RuntimeException error = new RuntimeException("Error de conexión");
        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.error(error));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void obtenerTecnologiasPorIds_CuandoListaConNull_DeberiaManejarNull() {
        List<Long> ids = Arrays.asList(1L, null, 2L);
        Tecnologia tecnologia2 = Tecnologia.builder()
                .id(2L)
                .nombre("Python")
                .descripcion("Lenguaje interpretado")
                .activa(true)
                .build();
        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologia, tecnologia2));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectNext(tecnologia)
                .expectNext(tecnologia2)
                .verifyComplete();
    }



    @Test
    void obtenerTecnologiasPorIds_CuandoMultiplesIds_DeberiaRetornarTodasLasTecnologias() {
        Tecnologia tecnologia1 = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .activa(true)
                .build();
        Tecnologia tecnologia2 = Tecnologia.builder()
                .id(2L)
                .nombre("Python")
                .descripcion("Lenguaje interpretado")
                .activa(true)
                .build();
        Tecnologia tecnologia3 = Tecnologia.builder()
                .id(3L)
                .nombre("JavaScript")
                .descripcion("Lenguaje de scripting")
                .activa(true)
                .build();
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        when(tecnologiaRepository.obtenerTecnologiasPorIds(anyList())).thenReturn(Flux.just(tecnologia1, tecnologia2, tecnologia3));

        StepVerifier.create(tecnologiaUseCase.obtenerTecnologiasPorIds(ids))
                .expectNext(tecnologia1)
                .expectNext(tecnologia2)
                .expectNext(tecnologia3)
                .verifyComplete();
    }

    @Test
    void activarTecnologias_CuandoIdsValidos_DeberiaActivarExitosamente() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        when(tecnologiaRepository.activarTecnologias(ids)).thenReturn(Mono.empty());

        StepVerifier.create(tecnologiaUseCase.activarTecnologias(ids))
                .verifyComplete();

        verify(tecnologiaRepository).activarTecnologias(ids);
    }

    @Test
    void activarTecnologias_CuandoListaVacia_DeberiaCompletarSinError() {
        List<Long> ids = Collections.emptyList();

        when(tecnologiaRepository.activarTecnologias(ids)).thenReturn(Mono.empty());

        StepVerifier.create(tecnologiaUseCase.activarTecnologias(ids))
                .verifyComplete();

        verify(tecnologiaRepository).activarTecnologias(ids);
    }

    @Test
    void activarTecnologias_CuandoRepositoryFalla_DeberiaPropagarError() {
        List<Long> ids = Arrays.asList(1L, 2L);
        RuntimeException error = new RuntimeException("Error de base de datos");

        when(tecnologiaRepository.activarTecnologias(ids)).thenReturn(Mono.error(error));

        StepVerifier.create(tecnologiaUseCase.activarTecnologias(ids))
                .expectError(RuntimeException.class)
                .verify();

        verify(tecnologiaRepository).activarTecnologias(ids);
    }

    @Test
    void desactivarTecnologias_CuandoIdsValidos_DeberiaDesactivarExitosamente() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        when(tecnologiaRepository.desactivarTecnologias(ids)).thenReturn(Mono.empty());

        StepVerifier.create(tecnologiaUseCase.desactivarTecnologias(ids))
                .verifyComplete();

        verify(tecnologiaRepository).desactivarTecnologias(ids);
    }

    @Test
    void desactivarTecnologias_CuandoListaVacia_DeberiaCompletarSinError() {
        List<Long> ids = Collections.emptyList();

        when(tecnologiaRepository.desactivarTecnologias(ids)).thenReturn(Mono.empty());

        StepVerifier.create(tecnologiaUseCase.desactivarTecnologias(ids))
                .verifyComplete();

        verify(tecnologiaRepository).desactivarTecnologias(ids);
    }

    @Test
    void desactivarTecnologias_CuandoRepositoryFalla_DeberiaPropagarError() {
        List<Long> ids = Arrays.asList(1L, 2L);
        RuntimeException error = new RuntimeException("Error de base de datos");

        when(tecnologiaRepository.desactivarTecnologias(ids)).thenReturn(Mono.error(error));

        StepVerifier.create(tecnologiaUseCase.desactivarTecnologias(ids))
                .expectError(RuntimeException.class)
                .verify();

        verify(tecnologiaRepository).desactivarTecnologias(ids);
    }
}

