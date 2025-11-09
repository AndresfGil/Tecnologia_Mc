package co.com.tecnologia.model.tecnologia;
import lombok.*;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tecnologia {

    private Long id;
    private String nombre;
    private String descripcion;
}
