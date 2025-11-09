package co.com.tecnologia.api.helpers;

import co.com.tecnologia.api.dto.TecnologiaBatchResponseDto;
import co.com.tecnologia.api.dto.TecnologiaRequestDto;
import co.com.tecnologia.api.dto.TecnologiaResponseDto;
import co.com.tecnologia.model.tecnologia.Tecnologia;
import org.springframework.stereotype.Component;

@Component
public class TecnologiaMapper {
    
    public Tecnologia toDomain(TecnologiaRequestDto dto) {
        return Tecnologia.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .build();
    }

    public TecnologiaResponseDto toResponseDto(Tecnologia tecnologia) {
        return new TecnologiaResponseDto(
                tecnologia.getId(),
                tecnologia.getNombre(),
                tecnologia.getDescripcion()
        );
    }

    public TecnologiaBatchResponseDto toBatchResponseDto(Tecnologia tecnologia) {
        return new TecnologiaBatchResponseDto(
                tecnologia.getId(),
                tecnologia.getNombre()
        );
    }
}
