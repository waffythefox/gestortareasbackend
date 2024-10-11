package com.tareas.gestortareas;

public class UserDTO {
    private Long id;
    private String username;

    // Constructor, Getters y Setters

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

