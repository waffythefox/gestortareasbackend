package com.tareas.gestortareas;

import java.time.ZonedDateTime;

public class CommentDTO {
    private Long id;
    private String content;
    private UserDTO user;  // Relacionar con UserDTO en lugar de User
    private ZonedDateTime createdAt;

    public CommentDTO(Long id, String content, UserDTO user, ZonedDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
