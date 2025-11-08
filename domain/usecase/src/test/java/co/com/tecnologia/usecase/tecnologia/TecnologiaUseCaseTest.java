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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
                .idTecnologia(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
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
}

