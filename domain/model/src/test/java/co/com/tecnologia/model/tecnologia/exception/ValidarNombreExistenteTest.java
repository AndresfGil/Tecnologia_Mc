package co.com.tecnologia.model.tecnologia.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidarNombreExistenteTest {

    @Test
    void constructor_DeberiaInicializarConMensajeCorrecto() {
        String nombre = "Java";
        ValidarNombreExistente exception = new ValidarNombreExistente(nombre);

        assertEquals("El nombre de la tecnología ya está registrado", exception.getMessage());
        assertEquals("NOMBRE_EXISTENTE", exception.getErrorCode());
        assertEquals("Nombre no disponible", exception.getTitle());
        assertEquals(409, exception.getStatusCode());
        assertNotNull(exception.getErrors());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().get(0).contains(nombre));
    }

    @Test
    void constructor_ConNombreDiferente_DeberiaIncluirNombreEnMensaje() {
        String nombre = "Python";
        ValidarNombreExistente exception = new ValidarNombreExistente(nombre);

        assertTrue(exception.getErrors().get(0).contains("Python"));
        assertEquals(String.format("Ya existe una tecnología con el nombre '%s'", nombre), 
                     exception.getErrors().get(0));
    }

    @Test
    void constructor_DeberiaSerInstanciaDeBaseException() {
        ValidarNombreExistente exception = new ValidarNombreExistente("Test");

        assertTrue(exception instanceof BaseException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_ConNombreVacio_DeberiaFuncionarCorrectamente() {
        ValidarNombreExistente exception = new ValidarNombreExistente("");

        assertTrue(exception.getErrors().get(0).contains("''"));
    }

    @Test
    void constructor_ConNombreNull_DeberiaManejarNull() {
        ValidarNombreExistente exception = new ValidarNombreExistente(null);

        assertTrue(exception.getErrors().get(0).contains("null"));
    }
}

