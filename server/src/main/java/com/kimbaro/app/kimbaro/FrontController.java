package com.kimbaro.app.kimbaro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
public class FrontController {
    @Bean
    public RouterFunction<ServerResponse> staticRouterFunction() {
        return RouterFunctions.resources("/**", new ClassPathResource("/public"));
    }


    @Bean
    public RouterFunction<ServerResponse> mainRouterFuction(@Value("classpath:/static/index.html") Resource index) {
        return route(GET("/index.html"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(index));
    }

    @Bean
    public RouterFunction<ServerResponse> selectRouterFuction(@Value("classpath:/static/select.html") Resource index) {
        return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(index));
    }
}
