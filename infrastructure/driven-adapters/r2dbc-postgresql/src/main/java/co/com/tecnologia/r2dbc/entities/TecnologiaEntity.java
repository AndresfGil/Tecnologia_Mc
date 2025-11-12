package co.com.tecnologia.r2dbc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tecnologias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TecnologiaEntity {

    @Id
    @Column("id_tecnologia")
    private Long id;

    private String nombre;

    private String descripcion;

    private Boolean activa;

    @Column("fecha_modificacion")
    private LocalDateTime fechaModificacion;
}
