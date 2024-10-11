package com.tareas.gestortareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GestorTareasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestorTareasApplication.class, args);
    }
}