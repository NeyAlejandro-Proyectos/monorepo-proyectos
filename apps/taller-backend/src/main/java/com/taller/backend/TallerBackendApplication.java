package com.taller.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada principal de la aplicacion Spring Boot.
 *
 * @SpringBootApplication es un atajo que combina:
 *   - @Configuration       : marca esta clase como fuente de beans
 *   - @EnableAutoConfiguration : activa la configuracion automatica de Spring Boot
 *   - @ComponentScan       : escanea todos los componentes del paquete raiz
 */
@SpringBootApplication
public class TallerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TallerBackendApplication.class, args);
    }
}
