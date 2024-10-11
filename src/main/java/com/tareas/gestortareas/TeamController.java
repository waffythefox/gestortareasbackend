package com.tareas.gestortareas;

import com.tareas.gestortareas.Team;
import com.tareas.gestortareas.TeamRepository;
import com.tareas.gestortareas.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los equipos
    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Crear un nuevo equipo
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody TeamRequest teamRequest) {
        // Obtener el administrador
        User admin = userRepository.findById(teamRequest.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));

        // Obtener la lista de integrantes
        List<User> members = userRepository.findAllById(teamRequest.getMemberIds());

        // Crear y guardar el equipo
        Team team = new Team();
        team.setName(teamRequest.getName());
        team.setAdmin(admin);
        team.setMembers(members);
        teamRepository.save(team);

        return ResponseEntity.ok(team);
    }

    // Obtener un equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un equipo
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody TeamRequest teamRequest) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + id));

        // Obtener el administrador actualizado
        User admin = userRepository.findById(teamRequest.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));

        // Obtener la lista de integrantes actualizada
        List<User> members = userRepository.findAllById(teamRequest.getMemberIds());

        team.setName(teamRequest.getName());
        team.setAdmin(admin);
        team.setMembers(members);
        Team updatedTeam = teamRepository.save(team);
        return ResponseEntity.ok(updatedTeam);
    }

    // Eliminar un equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + id));
        teamRepository.delete(team);
        return ResponseEntity.noContent().build();
    }
}
