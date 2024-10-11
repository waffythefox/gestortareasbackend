package com.tareas.gestortareas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;  // Para buscar la tarea

    @Autowired
    private UserRepository userRepository;  // Para buscar el usuario (si aplica)

    // Obtener todos los comentarios (opcional: modificar para que devuelva solo los necesarios con DTO)
    @GetMapping
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        // Convertir cada Comment en un CommentDTO
        return comments.stream().map(this::convertToCommentDTO).collect(Collectors.toList());
    }

    // Crear un comentario asociado a una tarea
    @PostMapping("/task/{taskId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long taskId, @RequestBody Comment comment) {
        // Buscar la tarea a la que se asociará el comentario
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // Asignar la tarea al comentario
        comment.setTask(task);

        // Asignar el usuario al comentario si está presente en el request (opcional)
        if (comment.getUser() != null && comment.getUser().getId() != null) {
            User user = userRepository.findById(comment.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            comment.setUser(user);
        }

        // Guardar el comentario
        Comment newComment = commentRepository.save(comment);

        // Convertir `Comment` a `CommentDTO` antes de devolver la respuesta
        CommentDTO commentDTO = convertToCommentDTO(newComment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    // Listar los comentarios de una tarea específica
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTask(@PathVariable Long taskId) {
        // Buscar la tarea
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // Obtener los comentarios asociados a la tarea
        List<Comment> comments = commentRepository.findByTask(task);

        // Convertir la lista de comentarios a CommentDTO
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDTOs);
    }


    // Eliminar un comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        commentRepository.delete(comment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Método para convertir un `Comment` a `CommentDTO`
    private CommentDTO convertToCommentDTO(Comment comment) {
        UserDTO userDTO = convertToUserDTO(comment.getUser());
        return new CommentDTO(comment.getId(), comment.getContent(), userDTO, comment.getCreatedAt());
    }


    // Método para convertir un `User` a `UserDTO`
    private UserDTO convertToUserDTO(User user) {
        if (user != null) {
            return new UserDTO(user.getId(), user.getUsername());
        }
        return null;  // Si no hay usuario, retorna null
    }




}
