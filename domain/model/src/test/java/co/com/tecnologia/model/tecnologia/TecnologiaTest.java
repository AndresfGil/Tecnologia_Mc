package co.com.tecnologia.model.tecnologia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TecnologiaTest {

    @Test
    void builder_DeberiaCrearInstanciaConTodosLosCampos() {
        Tecnologia tecnologia = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Lenguaje de programación")
                .build();

        assertEquals(1L, tecnologia.getId());
        assertEquals("Java", tecnologia.getNombre());
        assertEquals("Lenguaje de programación", tecnologia.getDescripcion());
    }

    @Test
    void builder_DeberiaCrearInstanciaConCamposNulos() {
        Tecnologia tecnologia = Tecnologia.builder().build();

        assertNull(tecnologia.getId());
        assertNull(tecnologia.getNombre());
        assertNull(tecnologia.getDescripcion());
    }

    @Test
    void noArgsConstructor_DeberiaCrearInstanciaVacia() {
        Tecnologia tecnologia = new Tecnologia();

        assertNull(tecnologia.getId());
        assertNull(tecnologia.getNombre());
        assertNull(tecnologia.getDescripcion());
    }

    @Test
    void allArgsConstructor_DeberiaCrearInstanciaConTodosLosParametros() {
        Tecnologia tecnologia = new Tecnologia(1L, "Python", "Lenguaje interpretado");

        assertEquals(1L, tecnologia.getId());
        assertEquals("Python", tecnologia.getNombre());
        assertEquals("Lenguaje interpretado", tecnologia.getDescripcion());
    }

    @Test
    void toBuilder_DeberiaCrearNuevaInstanciaConValoresModificados() {
        Tecnologia original = Tecnologia.builder()
                .id(1L)
                .nombre("Java")
                .descripcion("Original")
                .build();

        Tecnologia modificada = original.toBuilder()
                .descripcion("Modificada")
                .build();

        assertEquals(1L, modificada.getId());
        assertEquals("Java", modificada.getNombre());
        assertEquals("Modificada", modificada.getDescripcion());
        assertNotSame(original, modificada);
    }

    @Test
    void setters_DeberiaModificarValores() {
        Tecnologia tecnologia = new Tecnologia();
        tecnologia.setId(2L);
        tecnologia.setNombre("JavaScript");
        tecnologia.setDescripcion("Lenguaje de scripting");

        assertEquals(2L, tecnologia.getId());
        assertEquals("JavaScript", tecnologia.getNombre());
        assertEquals("Lenguaje de scripting", tecnologia.getDescripcion());
    }
}



