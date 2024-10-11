package com.tareas.gestortareas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;  // Repositorio para obtener usuarios
    @Autowired
    private TeamRepository teamRepository;  // Repositorio para obtener equipos

    // Obtener todas las tareas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Crear una nueva tarea
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Cargar el usuario asignado si el ID está presente
        if (task.getAssignedUser() != null && task.getAssignedUser().getId() != null) {
            User user = userRepository.findById(task.getAssignedUser().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            task.setAssignedUser(user);
        }

        // Cargar el equipo si el ID está presente
        if (task.getTeam() != null && task.getTeam().getId() != null) {
            Team team = teamRepository.findById(task.getTeam().getId())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            task.setTeam(team);
        }

        // Guardar la tarea y devolverla
        Task newTask = taskRepository.save(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // Actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow();

        task.setTitle(taskDetails.getTitle());
        task.setContent(taskDetails.getContent());
        task.setStatus(taskDetails.getStatus());

        // Actualizar el usuario asignado si se proporciona
        if (taskDetails.getAssignedUser() != null && taskDetails.getAssignedUser().getId() != null) {
            User user = userRepository.findById(taskDetails.getAssignedUser().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            task.setAssignedUser(user);
        }

        // Actualizar el equipo si se proporciona
        if (taskDetails.getTeam() != null && taskDetails.getTeam().getId() != null) {
            Team team = teamRepository.findById(taskDetails.getTeam().getId())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            task.setTeam(team);
        }

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    // Eliminar una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

