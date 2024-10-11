package com.tareas.gestortareas;

import com.tareas.gestortareas.User;
import com.tareas.gestortareas.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Crear un nuevo usuario
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
