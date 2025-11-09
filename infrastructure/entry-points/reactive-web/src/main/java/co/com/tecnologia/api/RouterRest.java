package co.com.tecnologia.api;

import co.com.tecnologia.api.docs.TecnologiaControllerDocs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest implements TecnologiaControllerDocs {

    @Bean
    @Override
    public RouterFunction<ServerResponse> routerFunction(TecnologiaHandler handler) {
        return route(POST("/api/tecnologia"), handler::listenGuardarTecnologia)
                .andRoute(POST("/api/tecnologia/batch"), handler::listenObtenerTecnologiasPorIds);
    }
}
