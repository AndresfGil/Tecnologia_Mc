package co.com.tecnologia.schedule;

import co.com.tecnologia.model.tecnologia.gateways.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PurgeService {

    private static final Logger logger = Logger.getLogger(PurgeService.class.getName());
    private final TecnologiaRepository tecnologiaRepository;

    @Scheduled(cron = "0 0 3 * * *")
    public void purgeSoftDeleted() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        logger.log(Level.INFO, "Iniciando eliminacion de tecnologías inactivas anteriores a: {0}", fechaLimite);
        
        tecnologiaRepository.eliminarInactivasAntiguas(fechaLimite)
                .doOnSuccess(v -> logger.info("Eliminacion completada exitosamente"))
                .doOnError(error -> logger.log(Level.SEVERE, "Error en la eliminacion: {0}", error.getMessage()))
                .subscribe();
    }
}

