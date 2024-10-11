package com.tareas.gestortareas;

import com.tareas.gestortareas.User;
import com.tareas.gestortareas.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Verificar si el email ya está registrado
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(null); // El usuario ya existe
        }

        // Crear el nuevo usuario
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encriptar la contraseña

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    // Endpoint para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        // Buscar al usuario por email
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        // Verificar si el usuario existe y la contraseña es correcta
        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.ok("Inicio de sesión exitoso"); // Aquí deberías devolver un token si estás usando JWT
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}